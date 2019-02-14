package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.config.AppConfig;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.PdProductInfoService;
import com.kingyon.chengxin.product.dto.ProductInfoDetail;
import com.kingyon.chengxin.product.dto.ProductInfoDto;
import com.kingyon.chengxin.product.dto.query.ProductInfoQuery;
import com.kingyon.chengxin.product.dto.response.ChannelProductDto;
import com.kingyon.chengxin.product.dto.response.SupplierDictionariesDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/10/15 0015 14:15
 */

@RestController
@RequestMapping("/boss/product")
public class PdProductInfoController {


    @DubboReference
    PdProductInfoService productInfoService;

    @PostMapping("/v1/productList")
    public Response productList(@ModelAttribute ProductInfoQuery query) throws Exception {
        PageDto pageDto = productInfoService.productList(query);
        return new Response(pageDto);
    }

    ;

    @PostMapping("/v1/addOrEdit")
    public Response addOrEdit(@RequestBody @Validated ProductInfoDto infoDto) throws Exception {
        ProductInfoDto productInfoDto = productInfoService.addOrEdit(infoDto);
        return new Response(productInfoDto);
    }

    ;

    @GetMapping("/v1/channelProductList")
    public Response channelProductList(@RequestParam("channelId") Integer channelId) throws Exception {
        List<ChannelProductDto> qxProducts = productInfoService.channelProductList(channelId);
        return new Response(qxProducts);
    }

    ;

    @GetMapping("/v1/copySupplierProductInfo")
    public Response copySupplierProductInfo(@RequestParam("productCode") String productCode, @RequestParam("channelId") Integer channelId) throws Exception {
        ProductInfoDto productInfo = productInfoService.copySupplierProductInfo(productCode, channelId);
        return new Response(productInfo);
    }

    ;

    @PostMapping("/v1/getProductInfoDetail")
    public Response getProductInfoDetail(@RequestParam("productId") Long productId) throws Exception {
        ProductInfoDetail productInfoDetail = productInfoService.getProductInfoDetail(productId, (byte) 0);
        return new Response(productInfoDetail);
    }

    ;

    @GetMapping("/v1/SupplierList")
    public Response SupplierList() throws Exception {
        List<SupplierDictionariesDto> supplierDictionariesDtos = productInfoService.SupplierList();
        return new Response(supplierDictionariesDtos);
    }

    ;

    @PostMapping("/v1/changeSkuStatus")
    public Response changeSkuStatus(@RequestParam("productId") Long productId, @RequestParam("skuId") Long skuId) throws Exception {
        Boolean result = productInfoService.changeSkuStatus(productId, skuId);
        if (!result) {
            throw new WebException(ProductErrorCode.SAVE_FAIL);
        }
        return new Response(200, "修改成功");
    }

    ;

    @PostMapping("/v1/getProductH5Url")
    public Response changeSkuStatus(@RequestParam("productId") Long productId) throws Exception {
        String productH5Url = AppConfig.getProperty("common.productH5Url") + productId;
        return new Response(productH5Url);
    }
    ;


}
