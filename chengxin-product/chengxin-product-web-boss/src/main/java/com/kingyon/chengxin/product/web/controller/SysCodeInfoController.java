package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.SysCodeInfoService;
import com.kingyon.chengxin.product.dto.ProductInfoDto;
import com.kingyon.chengxin.product.dto.query.ProductInfoQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2019/1/4 0004.
 */

@RestController
@RequestMapping("/boss/code")
public class SysCodeInfoController {

    @DubboReference
    SysCodeInfoService sysCodeInfoService;

    @PostMapping("/v1/selectBrandCodeList")
    public Response selectBrandCodeList() throws Exception {
        List<Map<String, Object>> list = sysCodeInfoService.selectBrandCodeList();
        return new Response(list);
    }
    ;

    @PostMapping("/v1/addBrand")
    public Response addBrand(@RequestParam("brandName") String brandName,@RequestParam(value = "id",required = false) Long id) throws Exception {
        Boolean aBoolean = sysCodeInfoService.addBrand(brandName,id);
        if (!aBoolean) {
            throw new WebException(ProductErrorCode.SAVE_FAIL);
        }
        return new Response(200,"保存成功");
    }

    ;


}
