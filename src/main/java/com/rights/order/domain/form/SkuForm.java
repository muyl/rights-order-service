package com.rights.order.domain.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 拓仲 on 2020/3/16
 */
@Data
public class SkuForm {

    @NotEmpty(message = "活动号不能为空")
    private String actyId;
    private String actyName;

    private String qualfyLevel;
    private String visibleFlag;
    private String actyLife;

    //======================================================

    @NotEmpty(message = "礼品不能为空")
    private String giftId;
    private String giftName;

    @NotNull(message = "礼品数量不能为空")
    @Range(min = 1, max = 8,message = "礼品数量必须是1~8范围内")
    private Integer giftNum;


}
