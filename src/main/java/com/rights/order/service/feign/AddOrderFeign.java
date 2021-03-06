package com.rights.order.service.feign;

import com.rights.order.domain.model.FilterRuleModel;

import java.util.List;

/**
 * @author 拓仲 on 2020/3/16
 */
public interface AddOrderFeign {
    List<FilterRuleModel> getActyRuleList(String actyId, String scope);

    List<FilterRuleModel> getPrizeRuleList(String prizeId, String scope);
}
