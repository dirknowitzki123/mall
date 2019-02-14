/**
  * Copyright 2018 bejson.com 
  */
package com.kingyon.chengxin.insurance.dto.qxapidto;
import com.kingyon.chengxin.framework.dto.BaseDto;
import com.kingyon.chengxin.insurance.dto.qxapidto.BeneficiaryInfos;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2018-08-08 14:8:5
 */
@Data
public class Insurants extends BaseDto {

    private int insurantId;
    private int relationId;
    private int count;
    private BigDecimal singlePrice;
    private String cname;
    private int cardType;
    private String cardCode;
    private int sex;
    private String birthday;
    private String provCityId;
    private String contactAddress;
    private String mobile;
    private String job;
    private List<BeneficiaryInfos> beneficiaryInfos;

}