
package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.Date;

/**
 * Auto-generated: 2018-08-08 14:8:5
 */
@Data
public class Applicant extends BaseDto {

    private String cname;
    private int cardType;
    private String cardCode;
    private int sex;
    private String birthday;
    private String mobile;
    private String email;
    private String provCityId;
    private String contactAddress;
    private String job;
}