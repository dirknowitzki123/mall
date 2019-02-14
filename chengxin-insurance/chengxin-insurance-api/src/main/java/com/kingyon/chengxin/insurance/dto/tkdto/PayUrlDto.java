package com.kingyon.chengxin.insurance.dto.tkdto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

/**
 * @Auther: Aspen
 * @Created: 2018/11/23 0023.
 */
@Data
public class PayUrlDto extends BaseDto {
    private Long accid;
    private String orderNumber;
    private String callbackurl;
    private String failurl;
    //泰康支付方式 01 : 微信WAP支付 02 : 支付宝WAP支付 03：微信扫码支付 04：微信公众号oath支付
    private String payType;

}
