
package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2018-08-08 14:8:5
 */
@Data
public class InsureReq extends BaseDto {
    private Long accid;
    private Long productId;
    private String insureNum;
    private String caseCode;
    private String startDate;
    private String endDate;
    private Applicant applicant;
    private List<Insurants> insurants;
    private String priceArgs;
    private Map<String,Object> otherInfo = new HashMap<>();
    private String partnerUniqKey;
    private String bargainNum;


}