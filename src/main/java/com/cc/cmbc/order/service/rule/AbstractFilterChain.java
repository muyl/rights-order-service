package com.cc.cmbc.order.service.rule;


import com.cc.cmbc.order.domain.Result;

import java.util.Map;

/**
 * @author tony
 * @version 1.0
 * @date 2018/3/24 12:10
 */
public abstract class AbstractFilterChain {

    protected Map<String, Object> requestMap;
    private AbstractFilterChain nextFilter;
    private AbstractFilterChain previousFilter;

    /**
     * 校验
     *
     * @param paramMap 参数
     * @return 校验结果
     */
    public Result doFilter(Map<String, Object> paramMap){
        Result reply = this.checkFilter(paramMap);
        if (!reply.isSucc()){
            if (previousFilter != null){
                previousFilter.rollFilter(paramMap);
            }
        }else{
            if (nextFilter != null){
                reply = nextFilter.doFilter(paramMap);
            }
        }
        return reply;
    }

    /**
     * 回滚校验
     *
     * @param paramMap 参数
     * @return 校验结果
     */
    public void rollFilter(Map<String, Object> paramMap) {
        this.restoreFilter(paramMap);
        if (previousFilter != null) {
            previousFilter.rollFilter(paramMap);
        }
    }

    /**
     * 校验
     *
     * @param paramMap 参数
     * @return 恢复结果
     */
    public abstract Result checkFilter(Map<String, Object> paramMap);

    /**
     * 恢复
     *
     * @param paramMap 参数
     * @return 恢复结果
     */
    public abstract void restoreFilter(Map<String, Object> paramMap);

    public Map<String, Object> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap) {
        this.requestMap = requestMap;
    }

    public AbstractFilterChain getNextFilter() {
        return nextFilter;
    }

    public void setNextFilter(AbstractFilterChain nextFilter) {
        this.nextFilter = nextFilter;
    }

    public AbstractFilterChain getPreviousFilter() {
        return previousFilter;
    }

    public void setPreviousFilter(AbstractFilterChain previousFilter) {
        this.previousFilter = previousFilter;
    }
}
