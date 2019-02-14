package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.request.OrderRefund;
import com.kingyon.chengxin.product.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/24 0024 10:00
 */
@RestController
@RequestMapping("/boss/order")
public class OmOrderController {

    @DubboReference
    OmOrderService orderService;

    @PostMapping("/v1/orderList")
    public Response orderList(@ModelAttribute OrderQuery query) throws Exception {
        PageDto pageDto = orderService.orderList(query);
        return new Response(pageDto);
    }

    ;

    @PostMapping("/v1/orderDetail")
    public Response orderDetail(@RequestParam("orderNumber") String orderNumber) throws Exception {
        Map<String, Object> orderDetail = orderService.orderDetail(orderNumber);
        return new Response(orderDetail);
    }

    ;

    @PostMapping("/v1/orderRemark")
    public Response orderRemark(@RequestParam("orderNumber") String orderNumber, @RequestParam("remark") String remark) throws Exception {
        Boolean result = orderService.orderDetailRemark(orderNumber, remark);
        if (!result) {
            throw new WebException(ProductErrorCode.SAVE_FAIL);
        }
        return new Response(result);
    }

    ;


    @PostMapping("/v1/offlineRefund")
    public Response offlineRefund(@ModelAttribute @Validated OrderRefund orderRefund) throws Exception {
        Boolean result = orderService.offlineRefund(orderRefund);
        if (!result) {
            throw new WebException(ProductErrorCode.SAVE_FAIL, "退款失败,具体原因我还是不晓得");
        }
        return new Response(result);
    }

    ;


    @RequestMapping(value = "/v1/exportOrder", method = {RequestMethod.POST})
    public String createExcel(@ModelAttribute OrderQuery query, HttpServletResponse response) throws IOException {
        PageDto pageDto = orderService.orderList(query);
        List list = pageDto.getDataList();

        String[] titleArr = {"订单编号", "产品名称", "产品类型", "金额", "支付状态", "支付账号", "下单时间"};
        String[][] contentArr = new String[list.size()][7];
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> param = (Map) list.get(i);
            String productType = "";
            switch ((int) param.get("projectType")) {
                case 10001:
                    productType = "保险产品";
                    break;
                case 10002:
                    productType = "套餐产品";
                    break;
                case 10003:
                    productType = "医疗服务";
                    break;
            }
            String payStatus = "";
            switch ((int) param.get("payStatus")) {
                case 0:
                    payStatus = "未支付";
                    break;
                case 1:
                    payStatus = "已支付";
                    break;
                case 2:
                    payStatus = "已取消";
                    break;
                case 3:
                    payStatus = "已退款";
                    break;
            }

            contentArr[i][0] = (String) param.get("orderNumber");
            contentArr[i][1] = (String) param.get("productName");
            contentArr[i][2] = productType;
            contentArr[i][3] = String.valueOf(param.get("amount"));
            contentArr[i][4] = payStatus;
            contentArr[i][5] = (String) param.get("accountName");
            contentArr[i][6] = String.valueOf(param.get("orderTime"));
            ;
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

}
