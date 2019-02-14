package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.PdProductDocumentService;
import com.kingyon.chengxin.product.dto.ProductDocumentDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 09:51
 */

@RestController
@RequestMapping("/boss/productDocument")
public class PdProductDocumentController {


    @DubboReference
    PdProductDocumentService productDocumentService;

    @GetMapping("/v1/pullProductDoc")
    public Response pullProductDoc(@RequestParam(required = true) String productCode, @RequestParam(required = true) Long productId) throws Exception {
        List<ProductDocumentDto> documentDtoList  = productDocumentService.pullProductDoc(productCode,productId);
        return new Response(documentDtoList);
    };

    @GetMapping("/v1/productDocListByProductId")
    public Response productDocListByProductId(@RequestParam("productId" )Long productId) throws Exception {
        List<ProductDocumentDto> documentDtoList = productDocumentService.productDocListByProductId(productId);
        return new Response(documentDtoList);
    };

    @GetMapping("/v1/openBuyTold")
    public Response openBuyTold(@RequestParam("productId" )Long productId ,@RequestParam("docId") Long docId) throws Exception {
        List<ProductDocumentDto> documentDtoList = productDocumentService.openBuyTold(productId,docId);
        return new Response(documentDtoList);
    };

    @PostMapping("/v1/addOrEdit")
    public Response addOrEdit(@ModelAttribute @Validated ProductDocumentDto documentDto) throws Exception {
        Boolean aBoolean = productDocumentService.addOrEdit(documentDto);
        if (!aBoolean) {
            throw new WebException(ProductErrorCode.SAVE_FAIL,"操作失败");
        }
        return new Response(aBoolean);
    };

    @GetMapping("/v1/delete")
    public Response delete(@RequestParam("id") Long id  ) throws Exception {
        Boolean aBoolean = productDocumentService.delete(id);
        if (!aBoolean) {
            throw new WebException(ProductErrorCode.SAVE_FAIL,"操作失败");
        }
        return new Response(aBoolean);
    };

}
