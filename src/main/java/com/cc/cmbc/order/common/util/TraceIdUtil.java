package com.cc.cmbc.order.common.util;

import java.util.UUID;

/**
 * @author 拓仲 on 2020/3/28
 */
public class TraceIdUtil {
    public static String getTraceId() {
        return UUIDUtils.getUUID().toUpperCase();
    }
}
