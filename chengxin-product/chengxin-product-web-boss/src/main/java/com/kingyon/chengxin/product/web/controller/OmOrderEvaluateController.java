package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.product.api.OmOrderEvaluateService;
import com.kingyon.chengxin.product.dto.OrderEvaluateDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 10:52
 */
@RestController
@RequestMapping("/boss/OrderEvaluate")
public class OmOrderEvaluateController {


    @DubboReference
    OmOrderEvaluateService orderEvaluateService;

    @PostMapping("/v1/addOrEdit")
    public Response addOrEdit(@ModelAttribute @Validated OrderEvaluateDto evaluateDto) throws Exception {
        Boolean aBoolean =  orderEvaluateService.addOrEdit(evaluateDto);
         return new Response(aBoolean);
    };

    @GetMapping("/v1/delete")
    public Response delete(@RequestParam("id") Long id ) throws Exception {
        Boolean aBoolean = orderEvaluateService.delete(id);
        return new Response(aBoolean);
    };

    @GetMapping("/v1/evaluateBossList")
    public Response evaluateBossList(@RequestParam("productId") Long productId ) throws Exception {
        List<OrderEvaluateDto> evaluateBossList = orderEvaluateService.evaluateBossList(productId);
        return new Response(evaluateBossList);
    };


}
