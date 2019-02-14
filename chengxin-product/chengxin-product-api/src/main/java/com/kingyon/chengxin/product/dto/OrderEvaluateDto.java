package com.kingyon.chengxin.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 10:47
 */
@Data
public class OrderEvaluateDto extends BaseDto {


    private Long id;
    /**
     * 评价内容
     * @time 2018-10-17 10:30:25
     */
    private String content;

    /**
     * 1就诊2保险3体检4基因5其他6套餐
     * @time 2018-10-17 10:30:25
     */
    private Integer projectType;

    /**
     * 根据项目类型关联相应项目id
     * @time 2018-10-17 10:30:25
     */
    private Long evaluateProjectId;

    /**
     * 0否1是
     * @time 2018-10-17 10:30:25
     */
    private Boolean visible;

    /**
     * 0否后台添加1是后台添加
     * @time 2018-10-17 10:30:25
     */
    private Boolean evaluateType;

    /**
     * 评分
     * @time 2018-10-17 10:30:25
     */
    @Max(value = 5)
    private Double score;

    @NotNull
    private String fakeAccount;

    private String fakePrc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

}
