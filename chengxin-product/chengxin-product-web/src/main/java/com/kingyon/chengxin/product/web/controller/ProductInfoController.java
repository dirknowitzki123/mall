package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.annotation.NoAuthorize;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.PdProductInfoService;
import com.kingyon.chengxin.product.dto.ProductInfoDetail;
import com.kingyon.chengxin.product.dto.query.ProductInfoQuery;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/15 0015 14:15
 */

@RestController
@RequestMapping("/api/product")
public class ProductInfoController {

    @DubboReference
    PdProductInfoService productInfoService;

    @NoAuthorize
    @PostMapping("/v1/selectProductList")
    public Response selectProductList(@ModelAttribute ProductInfoQuery query) throws Exception {
        PageDto pageDto = productInfoService.selectProductList(query);
        return new Response(pageDto);
    }

    ;


    @RequestMapping(value = "/v1/getProductDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public Response getProductDetail(@RequestParam("productId") Long productId) throws Exception {
        ProductInfoDetail productInfoDetail = productInfoService.getProductInfoDetail(productId, (byte) 1);
        if (productInfoDetail.getPutaway() == 0) {
            throw new WebException(ProductErrorCode.NOT_FOUND_PRODUCT, "产品已下架");
        }
        return new Response(productInfoDetail);
    }

    ;


    @PostMapping("/v1/defaultTrial")
    public Response defaultTrial(@RequestParam("productId") Long productId) throws Exception {
        Object productInfoDetail = productInfoService.defaultTrial(productId);
        return new Response(productInfoDetail);
    }

    ;


    @PostMapping("/v1/productInsuredAttr")
    public Response productInsuredAttr(@RequestParam("productId") Long productId) throws Exception {
        Map<String, Object> productInfoDetail = productInfoService.productInsuredAttr(productId);
        return new Response(productInfoDetail);
    }

    ;

}
