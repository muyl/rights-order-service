package com.cc.cmbc.order.service;

import com.cc.cmbc.order.common.constant.RedisConstants;
import com.cc.cmbc.order.common.exception.LogicException;
import com.cc.cmbc.order.common.support.RedisClusterLock;
import com.cc.cmbc.order.domain.form.AddOrderForm;
import com.cc.cmbc.order.domain.vo.AddOrderVo;
import com.cc.cmbc.order.service.filter.AddOrderExecute;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 拓仲 on 2020/3/16
 */
@Service
public class AddOrderFactory {

    @Autowired
    private AddOrderExecute  addOrderExecute;
    @Autowired
    private AddOrderService  addOrderService;
    @Autowired
    private RedisClusterLock redisClusterLock;

    public AddOrderVo addOrder(AddOrderForm addOrderForm) {
        AddOrderVo addOrderVo = null;
        try {
            if (createOrderLock(addOrderForm)) {
                if (preExecute(addOrderForm)) {
                    addOrderVo = execute(addOrderForm);
                }
            }
        } finally {
            releaseCreateLock(addOrderForm);
        }
        return addOrderVo;
    }

    private AddOrderVo execute(AddOrderForm addOrderForm) {
        AddOrderVo addOrderVo;
        try {
            addOrderVo = addOrderService.addOrder(addOrderForm);
        } catch (LogicException e) {
            rollBackExecute(addOrderForm);
            throw e;
        }
        postExecute(addOrderForm);
        return addOrderVo;
    }

    private boolean rollBackExecute(AddOrderForm addOrderForm) {
        return addOrderExecute.rollBackExecute(addOrderForm);
    }

    private boolean postExecute(AddOrderForm addOrderForm) {
        return addOrderExecute.postExecute(addOrderForm);
    }

    private void releaseCreateLock(AddOrderForm addOrderForm) {
        String key = Joiner.on(":").join(RedisConstants.ADD_ORDER_LOCK, addOrderForm.getUserKey());
        redisClusterLock.unLock(key, addOrderForm.getRequestId());
    }

    private boolean preExecute(AddOrderForm addOrderForm) {
        return addOrderExecute.preExecute(addOrderForm);
    }

    private boolean createOrderLock(AddOrderForm addOrderForm) {
        String key = Joiner.on(":").join(RedisConstants.ADD_ORDER_LOCK, addOrderForm.getUserKey());
        return redisClusterLock.tryLock(key, addOrderForm.getRequestId());
    }


}
