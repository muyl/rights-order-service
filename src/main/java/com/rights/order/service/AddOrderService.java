package com.rights.order.service;

import com.rights.order.domain.form.AddOrderForm;
import com.rights.order.domain.vo.AddOrderVo;

import javax.validation.Valid;

/**
 * @author 拓仲 on 2020/3/16
 */
public interface AddOrderService {

    AddOrderVo addOrder(@Valid AddOrderForm addOrderForm);
    
    
}
