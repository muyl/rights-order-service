package com.cc.cmbc.order.service.rule;

import com.cc.cmbc.order.common.constant.RedisConstants;
import com.cc.cmbc.order.common.support.RedisSupport;
import com.cc.cmbc.order.common.util.JsonUtil;
import com.cc.cmbc.order.domain.model.FilterRuleModel;
import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tony
 * @version 1.0
 * @date 2018/3/24 12:39
 */

public abstract class AbstractFilterSupport {

    @Resource
    private RedisSupport  redisSupport;



    /**
     * 获取活动规则列表
     *
     * @param actyId 活动号
     * @param scope  作用域
     * @return
     */
    protected List<FilterRuleModel> getActyRuleList(final String actyId, final String scope) {
        String itemKey = Joiner.on(":").join(RedisConstants.ACTY_RULE_LIST, actyId);
        String text = redisSupport.getHashKey(itemKey, scope);
        //2.获取规则列表
        if (StringUtils.isNotEmpty(text)) {
            return JsonUtil.convertJSONToArray(text, FilterRuleModel.class);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取奖品规则列表
     *
     * @param prizeId 奖品编号
     * @param scope   作用域
     * @return
     */
    protected List<FilterRuleModel> getPrizeRuleList(final String prizeId, final String scope) {
        if (StringUtils.isEmpty(prizeId)) {
            return new ArrayList<>();
        }
        String itemKey = Joiner.on(":").join(RedisConstants.PRIZE_RULE_LIST, prizeId);
        String text = redisSupport.getHashKey(itemKey, scope);
        //2.获取规则列表
        if (StringUtils.isNotEmpty(text)) {
            return JsonUtil.convertJSONToArray(text, FilterRuleModel.class);
        } else {
            return new ArrayList<>();
        }
    }
}
