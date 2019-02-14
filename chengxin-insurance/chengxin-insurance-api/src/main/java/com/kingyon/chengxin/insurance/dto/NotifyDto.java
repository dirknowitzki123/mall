package com.kingyon.chengxin.insurance.dto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Aspen
 * @create: 2018-07-23.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDto extends BaseDto {
    private  int notifyType;

    private Integer partnerId;

    private Long accid;

    private String caseCode;

    private String insureNum;

    private String onlinePaymentId;

    private Long price;

    private String payTime;

    private Boolean state;

    private String failMsg;

    private String newInsureNum;

    private String cancelInsurants;

    private String cancelMsg;

    private String otherInfo;

    private List<PolicyInfoDto>  policyInfo = new ArrayList<>();

}
