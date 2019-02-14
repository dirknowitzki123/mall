
package com.kingyon.chengxin.insurance.dto.qixindto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2018-08-03 13:33:31
 */
@Data
public class QxNotifyData extends BaseDto {
    /**
     * 产品编码
     */
    private String caseCode;
    /**
     * 投保单号
     */
    private String insureNum;
    /**
     * 在线支付网关标识
     * 1：支付宝 3：银联 14：财付通 21:微信
     * -11：银行代扣
     */
    private String onlinePaymentId;
    /**
     * 渠道id
     */
    private Long partnerId;
    /**
     * 渠道用户标识
     */
    private String partnerUniqueKey;
    /**
     * 支付时间，格式：yyyy-MM-dd HH:mm:ss
     */
    private String payTime;
    /**
     * 支付金额（单位：分）
     */
    private Long price;
    private List<QxPolicys> policys;
    private Boolean state;



}