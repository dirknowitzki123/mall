package com.kingyon.chengxin.insurance.dto.qxapidto;


import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HealthStatementReq extends BaseDto {
    private String caseCode;
    private List<GeneParam> genes = new ArrayList<>();
}
