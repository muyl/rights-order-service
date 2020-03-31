package com.cc.cmbc.order.web;

import com.cc.cmbc.order.domain.form.AddOrderForm;
import com.cc.cmbc.order.domain.vo.AddOrderVo;
import com.cc.cmbc.order.service.AddOrderFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 下单服务
 *
 * @author 拓仲 on 2020/3/15
 */
@RestController
@RequestMapping("/api/web/")
@Slf4j
public class OrderController extends BaseController {
    private Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private AddOrderFactory addOrderFactory;

    @RequestMapping(value = "createOrder.json", method = RequestMethod.POST, produces = "application/json")
    public AddOrderVo createOrder(@RequestBody @Valid AddOrderForm addOrderForm) {
        log.info("createOrder 请求参数：{}", addOrderForm);
        boolean isSucc = checkRequestParam(addOrderForm);
        AddOrderVo addOrderVo = null;
        if (isSucc) {
            addOrderVo = addOrderFactory.addOrder(addOrderForm);
        }
        return addOrderVo;

    }

    private boolean checkRequestParam(AddOrderForm addOrderForm) {
        return true;
    }
}
