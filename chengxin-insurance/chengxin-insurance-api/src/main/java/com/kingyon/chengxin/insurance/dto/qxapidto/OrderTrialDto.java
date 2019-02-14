package com.kingyon.chengxin.insurance.dto.qxapidto;
import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2018-08-08 10:3:28
 */
@Data
public class OrderTrialDto extends BaseDto {
    private String startDate;
    private String caseCode;
    private List<RestrictGenes> newRestrictGenes = new ArrayList<>();
    private RestrictGenes oldRestrictGene;
   
}