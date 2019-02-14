package com.kingyon.chengxin.insurance.dto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Aspen
 * @create: 2018-07-23.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyInfoDto extends BaseDto {

    private String insureNum;

    private String productName;

    private String planName;

    private String applicant;

    private String insurant;

    private String startDate;

    private String endDate;

    private String policyNum;
}
