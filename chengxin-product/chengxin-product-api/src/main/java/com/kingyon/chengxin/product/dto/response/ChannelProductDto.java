package com.kingyon.chengxin.product.dto.response;

import com.kingyon.chengxin.framework.annotation.FieldMapped;
import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

/**
 * @Auther: Aspen
 * @Date: 2018/10/15 0015 15:58
 */
@Data
public class ChannelProductDto extends BaseDto implements Comparable<ChannelProductDto> {
    private String prodName;
    @FieldMapped(names = "caseCode")
    private String productCode;
    private String companyName;
    private String planName;
    private int offShelf;
    private Boolean flag=false;

    @Override
    public int compareTo(ChannelProductDto o) {
        return o.getFlag()?-1:1;
    }
}
