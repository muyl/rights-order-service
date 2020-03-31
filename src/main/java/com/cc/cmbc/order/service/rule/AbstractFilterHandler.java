package com.cc.cmbc.order.service.rule;

import com.cc.cmbc.order.common.support.ApplicationSupport;
import com.cc.cmbc.order.common.util.JsonUtil;
import com.cc.cmbc.order.domain.model.FilterRuleModel;
import com.cc.cmbc.order.domain.Result;
import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @version 1.0
 * @date 2018/3/24 12:16
 */
@Component("filterHandler")
public class AbstractFilterHandler extends AbstractFilterSupport {

    private static final Logger logger        = LoggerFactory.getLogger(AbstractFilterHandler.class);
    private static final String FILTER_SERVIC = "ruleServiceFilter";

    @Autowired
    private ApplicationSupport applicationSupport;


    public Result doFilter(Map<String, Object> paramMap, String scope) {
        Result result;
        try {
            String actyId = (String) paramMap.get("actyId");
            String prizeId = (String) paramMap.get("giftId");
            List<AbstractFilterChain> filterChainList = getRuleFilterList(actyId, prizeId, scope);
            result = new Result(true);
            if (filterChainList != null && filterChainList.size() > 0) {
                int elementSize = filterChainList.size() - 1;
                AbstractFilterChain handler = filterChainList.get(elementSize);
                AbstractFilterChain endHandler = handler;
                for (int i = elementSize - 1; i >= 0; i--) {
                    handler = filterChainList.get(i);
                    handler.setNextFilter(endHandler);
                    endHandler.setPreviousFilter(handler);
                    endHandler = handler;
                }
                result = handler.doFilter(paramMap);
            }
            return result;
        } catch (Exception e) {
            logger.error("获取规则列表失败" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取规则列表
     *
     * @param actyId  活动号
     * @param prizeId 奖品号
     * @param scope   作用域
     * @return
     */
    private List<AbstractFilterChain> getRuleFilterList(final String actyId, String prizeId, final String scope) {
        List<FilterRuleModel> ruleList = new ArrayList<>();
        //1.获取活动规则
        List<FilterRuleModel> actyRuleList = getActyRuleList(actyId, scope);
        ruleList.addAll(actyRuleList);
        //3.获取奖品规则
        List<FilterRuleModel> prizeRuleList = getPrizeRuleList(prizeId, scope);
        ruleList.addAll(prizeRuleList);

        List<AbstractFilterChain> beanList = new ArrayList<>();
        for (FilterRuleModel filterRuleModel : ruleList) {
            String ruleId = filterRuleModel.getRuleId();
            String ruleParam = filterRuleModel.getRuleParam();
            String ruleServiceId = Joiner.on("").join(FILTER_SERVIC, ruleId);
            AbstractFilterChain lotteryFilterChain = applicationSupport.getBean(ruleServiceId, AbstractFilterChain.class);
            if (StringUtils.isNotBlank(ruleParam)) {
                lotteryFilterChain.setRequestMap(JsonUtil.convertObjectToJSONObject(ruleParam));
            }
            beanList.add(lotteryFilterChain);
        }
        return beanList;

    }


}
