package com.kingyon.chengxin.product.dto;

import com.kingyon.chengxin.product.dto.OrderEvaluateDto;
import com.kingyon.chengxin.product.dto.ProductDocumentDto;
import com.kingyon.chengxin.product.dto.ProductInfoDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 18:53
 */
@Data
public class ProductInfoDetail extends ProductInfoDto {

    private Integer evaluateSum;
    private List<ProductDocumentDto> productDocuments = new ArrayList<>();
    private List<OrderEvaluateDto> orderEvaluates = new ArrayList<>();


}
