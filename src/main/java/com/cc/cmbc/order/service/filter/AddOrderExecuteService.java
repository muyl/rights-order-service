package com.cc.cmbc.order.service.filter;

import com.cc.cmbc.order.common.constant.ActyConstants;
import com.cc.cmbc.order.common.constant.RedisConstants;
import com.cc.cmbc.order.common.constant.ResultEnum;
import com.cc.cmbc.order.common.exception.LogicException;
import com.cc.cmbc.order.common.support.RedisSupport;
import com.cc.cmbc.order.common.util.DateUtil;
import com.cc.cmbc.order.common.util.JsonUtil;
import com.cc.cmbc.order.domain.model.ActyLifeModel;
import com.cc.cmbc.order.domain.model.ActyDetailModel;
import com.cc.cmbc.order.domain.form.AddOrderForm;
import com.cc.cmbc.order.domain.form.SkuForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 拓仲 on 2020/3/16
 */
@Service
public class AddOrderExecuteService implements AddOrderExecute {

    @Autowired
    private RedisSupport redisSupport;

    @Override
    public boolean preExecute(AddOrderForm addOrderForm) {
        //1.验证活动信息
        List<SkuForm> skuFormList = addOrderForm.getSkuFormList();
        skuFormList.forEach(model -> {
            //组装活动信息
            assemblyActivityDetail(model);
            //组装礼品信息
            assemblyGiftDetail(model);
        });


        return false;
    }

    private void assemblyGiftDetail(SkuForm model) {

    }

    private void assemblyActivityDetail(SkuForm model) {
        String text = redisSupport.getHashKey(RedisConstants.ACTY_DETAIL_MAP, model.getActyId());
        if (StringUtils.isEmpty(text)) {
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_MESSAGE_MISS);
        }
        ActyDetailModel actyDetailModel = JsonUtil.convertJSONToObject(text, ActyDetailModel.class);
        if (actyDetailModel == null) {
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_MESSAGE_MISS);
        }

        //活动已下架
        if (ActyConstants.ACTY_STATUS_DOWN.equals(actyDetailModel.getActyStatus())) {
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_STATUS_DOWN);
        }
        //活动未上架
        if (!ActyConstants.ACTY_STATUS_UP.equals(actyDetailModel.getActyStatus())) {
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_STATUS_UP);
        }

        //活动兑换频率
        if (StringUtils.isEmpty(actyDetailModel.getActyLife())){
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_LIFE_MISS);
        }
        ActyLifeModel actyLifeModel = JsonUtil.convertJSONToObject(actyDetailModel.getActyLife(), ActyLifeModel.class);
        if (actyLifeModel == null){
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_LIFE_MISS);
        }
        //活动未开始
        if (!DateUtil.isAfterToday(actyLifeModel.getBeginDate())){
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_STATUS_START);
        }
        //活动已结束
        if (!DateUtil.isBeforeToday(actyLifeModel.getBeginDate())){
            throw new LogicException(ResultEnum.ADD_ORDER_ACTY_STATUS_END);
        }
    }

    @Override
    public boolean postExecute(AddOrderForm addOrderForm) {
        return false;
    }

    @Override
    public boolean rollBackExecute(AddOrderForm addOrderForm) {
        return false;
    }
}
