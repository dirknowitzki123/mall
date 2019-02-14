package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/9/30 0030 10:56
 */
@Data
public class Areas extends BaseDto {

    private String areaName;
    private String areaCode;
    private List<Areas> children = new ArrayList<>();
}
