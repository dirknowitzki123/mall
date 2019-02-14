package com.kingyon.chengxin.insurance.dto.qxapidto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

@Data
public class GeneParam extends BaseDto {

    private String key, protectItemId, value;
    private int sort;
}



