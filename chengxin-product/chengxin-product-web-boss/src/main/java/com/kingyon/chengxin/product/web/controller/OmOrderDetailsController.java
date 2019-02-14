package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderDetailsService;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.dto.query.OrderExportQuery;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.request.OperateRefund;
import com.kingyon.chengxin.product.dto.request.OrderRefund;
import com.kingyon.chengxin.product.enums.PayStatusEnum;
import com.kingyon.chengxin.product.enums.ProductType;
import com.kingyon.chengxin.product.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/boss/order")
public class OmOrderDetailsController {

	@DubboReference
	OmOrderService orderService;

	@DubboReference
	OmOrderDetailsService orderDetailsService;

	@PostMapping("/v2/orderList")
	public Response orderList(@ModelAttribute OrderQuery query) throws Exception {
		PageDto pageDto = orderService.orderList(query);
		return new Response(pageDto);
	}

	@PostMapping("/v2/orderDetail")
	public Response orderDetail(@RequestParam("orderNumber") String orderNumber) throws Exception {
		Map<String, Object> orderDetail = orderService.orderDetail(orderNumber);
		return new Response(orderDetail);
	}

	@PostMapping("/v2/orderRemark")
	public Response orderRemark(@RequestParam("orderNumber") String orderNumber, @RequestParam("remark") String remark) throws Exception {
		Boolean result = orderService.orderDetailRemark(orderNumber, remark);
		if (!result) {
			throw new WebException(ProductErrorCode.SAVE_FAIL);
		}
		return new Response(result);
	}

	@RequestMapping(value = "/v2/exportOrder", method = {RequestMethod.POST})
	public String createExcel(@ModelAttribute OrderExportQuery query, HttpServletResponse response) throws IOException {
		PageDto pageDto = orderService.orderListExport(query);
		List list = pageDto.getDataList();

		String[] titleArr = {"订单编号", "产品名称", "产品类型", "数量", "金额", "支付状态", "支付账号", "下单时间"};
		String[][] contentArr = new String[list.size()][8];
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> param = (Map) list.get(i);
			String productType = ProductType.getMessage((Integer) param.get("projectType"));
			String payStatus = PayStatusEnum.getMessage((Integer) param.get("payStatus"));
			contentArr[i][0] = (String) param.get("orderNumber");
			contentArr[i][1] = (String) param.get("productName");
			contentArr[i][2] = productType;
			contentArr[i][3] = String.valueOf(param.get("buy_num"));
			contentArr[i][4] = String.valueOf(param.get("amount"));
			contentArr[i][5] = payStatus;
			contentArr[i][6] = (String) param.get("accountName");
			contentArr[i][7] = String.valueOf(param.get("orderTime"));
		}

		String sheetName = "订单列表";
		HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, titleArr, contentArr);
		//输出Excel文件
		OutputStream output = response.getOutputStream();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=orderChecklist.xls");//文件名这里可以改
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/msexcel");
		workbook.write(output);
		output.close();
		return null;
	}

	@PostMapping("/v1/agreeRefundOrNot")
	public Response agreeRefundOrNot(@ModelAttribute @Validated OperateRefund operateRefund) throws Exception {
		boolean result = orderDetailsService.agreeRefundOrNot(operateRefund);
		if (!result) {
			throw new WebException(ProductErrorCode.SAVE_FAIL, "退款操作失败");
		}
		return new Response(result);
	}

	/*@PostMapping("/v1/refundRejectReason")
	public Response refundRejectReason(@RequestParam("orderNumber") String orderNumber,
									   @RequestParam("rejectReason") String rejectReason) throws Exception {
		boolean result = orderDetailsService.refundRejectReason(orderNumber, rejectReason);
		if (!result) {
			throw new WebException(ProductErrorCode.SAVE_FAIL);
		}
		return new Response(result);
	}*/

	@PostMapping("/v1/expressInfo")
	public Response expressInfo(@RequestParam("orderNumber") String orderNumber,
								@RequestParam("expressCompany") String expressCompany,
							    @RequestParam("expressNo") String expressNo) throws Exception {
		boolean result = orderDetailsService.saveExpressInfo(orderNumber, expressCompany, expressNo);
		if (!result) {
			throw new WebException(ProductErrorCode.SAVE_FAIL);
		}
		return new Response(result);
	}
}
