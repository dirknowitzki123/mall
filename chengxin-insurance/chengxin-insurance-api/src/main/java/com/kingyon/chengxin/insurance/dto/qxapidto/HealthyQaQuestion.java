package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/9/10 0010 11:19
 */
@Data
public class HealthyQaQuestion extends BaseDto {

    private Integer questionId;
    private Integer parentId;
    private Integer questionSort;
    private List<HealthyQaAnswer> healthyQaAnswers = new ArrayList<>();
    private List<HealthyQaQuestion> childrens = new ArrayList<>();
}
