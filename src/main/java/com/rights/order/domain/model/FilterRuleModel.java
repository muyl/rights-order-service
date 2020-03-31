package com.rights.order.domain.model;

import lombok.Data;

/**
 * @author 拓仲 on 2020/3/16
 */
@Data
public class FilterRuleModel {
    private Long id;
    private String ruleId;
    private String ruleName;
    private String ruleScope;
    private String ruleType;
    private String ruleParam;
    private Long seq;
    private String des;
}
