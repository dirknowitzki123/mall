package com.kingyon.chengxin.product.dto.request;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: Aspen
 * @Created: 2018/11/14 0014.
 */
@Data
public class OrderRefund extends BaseDto {

    /**
     * 退款订单编号
     * @time 2018-11-14 13:52:57
     */
    @NotNull
    private String orderNumber;

    /**
     * 退款金额
     * @time 2018-11-14 13:52:57
     */
    private BigDecimal refundMoney;

    /**
     * 退款原因
     * @time 2018-11-14 13:52:57
     */
    private String refundReason;


}
