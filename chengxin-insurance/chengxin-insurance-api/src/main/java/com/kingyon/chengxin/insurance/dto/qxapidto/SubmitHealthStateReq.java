package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/9/10 0010 11:07
 */
@Data
public class SubmitHealthStateReq extends BaseDto {
    private String caseCode;
    private List<GeneParam> genes = new ArrayList<>();
    private HealthyQa qaAnswer;

}
