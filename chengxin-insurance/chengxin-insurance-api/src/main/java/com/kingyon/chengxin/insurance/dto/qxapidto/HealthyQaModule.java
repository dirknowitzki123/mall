/**
 * Copyright 2018 bejson.com
 */
package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2018-09-10 11:13:49
 *
 */
@Data
public class HealthyQaModule extends BaseDto {

    private String moduleId;
    private List<HealthyQaQuestion> healthyQaQuestions;

}