package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.dto.OrderDto;
import com.kingyon.chengxin.product.dto.ProductInfoDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/24 0024 10:00
 */
@RestController
@RequestMapping("/api/order")
public class OmOrderController {

    @DubboReference
    OmOrderService orderService;

    @PostMapping("/v1/queryOrder")
    public Response queryOrder(@RequestParam("accid") Long accid, @RequestParam("orderNumber") String orderNumber) throws Exception {
        Map<String, Object> orderDto = orderService.queryOrder(accid, orderNumber);
        return new Response(orderDto);
    };

    @PostMapping("/v1/checkOrder")
    public Response checkOrder(@RequestParam("accid") Long accid, @RequestParam("orderNumber") String orderNumber) throws Exception {
        Map<String, Object> orderDto = orderService.checkOrder(accid, orderNumber);
        return new Response(orderDto);
    };

    @PostMapping("/v1/createOrder")
    public Response createOrder(@RequestParam("accid") Long accid, @RequestParam("productId") Long productId, @RequestParam("price") BigDecimal price) throws Exception {
        String orderNumber = orderService.createOrder(accid, productId, price, null,null);
        return new Response( Collections.singletonMap("orderNumber",orderNumber));
    };


}
