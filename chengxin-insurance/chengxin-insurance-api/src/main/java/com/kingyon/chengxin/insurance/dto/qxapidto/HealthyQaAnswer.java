/**
 * Copyright 2018 bejson.com
 */
package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

/**
 *
 */
@Data
public class HealthyQaAnswer extends BaseDto {

    private int answerId;
    private String answerValue;
    private String keyCode;

}