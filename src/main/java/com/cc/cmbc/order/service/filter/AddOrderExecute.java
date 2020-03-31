package com.cc.cmbc.order.service.filter;

import com.cc.cmbc.order.domain.form.AddOrderForm;

/**
 * @author 拓仲 on 2020/3/16
 */
public interface AddOrderExecute {

    /**
     * 校验
     *
     * @param addOrderForm 请求参数
     * @return 处理结果
     */
    boolean preExecute(AddOrderForm addOrderForm);

    /**
     * 完成
     *
     * @param addOrderForm 请求参数
     * @return 处理结果
     */
    boolean postExecute(AddOrderForm addOrderForm);

    /**
     * 回滚
     *
     * @param addOrderForm 请求参数
     * @return 处理结果
     */
    boolean rollBackExecute(AddOrderForm addOrderForm);
}
