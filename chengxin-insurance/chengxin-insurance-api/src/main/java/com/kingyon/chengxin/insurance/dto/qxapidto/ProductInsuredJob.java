package com.kingyon.chengxin.insurance.dto.qxapidto;


import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductInsuredJob extends BaseDto {

    private String id;
    private String name;
    private String parentId;
    private String isInsure;
    List<ProductInsuredJob> children = new ArrayList<>();


}
