package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/9/10 0010 11:10
 */

@Data
public class HealthyQa extends BaseDto {
    private String healthyId;
    private List<HealthyQaModule> healthyQaModules = new ArrayList<>();



}
