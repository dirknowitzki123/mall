package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderDetailsService;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.request.CreateOrderDto;
import com.kingyon.chengxin.product.dto.request.ReceiveAddressDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OmOrderDetailsController {

	@DubboReference
	OmOrderService orderService;

	@DubboReference
	OmOrderDetailsService orderDetailsService;

	@PostMapping("/v2/createOrder")
	public Response createOrder(@ModelAttribute @Validated CreateOrderDto createOrderDto) throws Exception {
		String orderNumber = orderService.createMallOrder(createOrderDto);
		return new Response(Collections.singletonMap("orderNumber", orderNumber));
	}

	@PostMapping("/v1/orderList")
	public Response orderList(@ModelAttribute OrderQuery query) throws Exception {
		PageDto pageDto = orderService.orderList(query);
		return new Response(pageDto);
	}

	@PostMapping("/v1/orderDetail")
	public Response orderDetail(@RequestParam("orderNumber") String orderNumber) throws Exception {
		Map<String, Object> orderDetail = orderDetailsService.orderDetail(orderNumber);
		return new Response(orderDetail);
	}

	@PostMapping("/v1/receiveAddressList")
	public Response receiveAddressList(@RequestParam("accid") Long accid) throws Exception {
		PageDto pageDto = orderDetailsService.receiveAddressList(accid);
		return new Response(pageDto);
	}

	@PostMapping("/v1/defaultReceiveAddress")
	public Response defaultReceiveAddress(@RequestParam("accid") Long accid) throws Exception {
		PageDto pageDto = orderDetailsService.defaultReceiveAddress(accid);
		return new Response(pageDto);
	}

	@PostMapping("/v1/addReceiveAddress")
	public Response addReceiveAddress(@ModelAttribute @Validated ReceiveAddressDto addressDto) throws Exception {
		boolean result = orderDetailsService.addReceiveAddress(addressDto);
		if (!result) {
			throw new WebException(ProductErrorCode.SAVE_FAIL);
		}
		return new Response(result);
	}

	@PostMapping("/v1/confirmReceipt")
	public Response confirmReceipt(@RequestParam("accid") Long accid, @RequestParam("orderNumber") String orderNumber) throws Exception {
		boolean result = orderDetailsService.confirmReceipt(accid, orderNumber);
		if (!result) {
			throw new WebException(ProductErrorCode.SAVE_FAIL);
		}
		return new Response(result);
	}
}
