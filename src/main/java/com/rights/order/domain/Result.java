package com.rights.order.domain;

import lombok.Data;

/**
 * @author 拓仲 on 2020/3/16
 */
@Data
public class Result {

    private String code;
    private String msg;
    private boolean isSucc;

    public Result() {
    }

    public Result(boolean isSucc) {
        this.isSucc = isSucc;
    }
}
