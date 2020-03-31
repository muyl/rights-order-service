package com.cc.cmbc.order.domain.form;

import com.cc.cmbc.order.common.util.UUIDUtils;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 拓仲 on 2020/3/15
 */
@Data
public class AddOrderForm implements Serializable {

    @NotBlank(message = "用户不能为空")
    private String userKey;

    @Valid
    @Size(min = 1 ,max =  10 , message = "列表中的元素数量为1~10")
    private List<SkuForm> skuFormList;


    private String requestId = UUIDUtils.getUUID();
}
