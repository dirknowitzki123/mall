package com.kingyon.chengxin.product.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.api.PayCenterService;
import com.kingyon.chengxin.product.api.UmUserAccountService;
import com.kingyon.chengxin.product.dto.UserAccountDto;
import com.kingyon.chengxin.product.web.wxutil.PayCommonUtil;
import com.kingyon.chengxin.product.web.wxutil.PropsValue;
import com.kingyon.chengxin.product.web.wxutil.WeixinPayUtils;
import com.kingyon.chengxin.product.web.wxutil.XMLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2019/1/9 0009.
 */
@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PayCenterController {


    @DubboReference
    UmUserAccountService userAccountService;

    @DubboReference
    OmOrderService orderService;



    @PostMapping("/wxPay/orderPay")
    public Response orderPay(@RequestParam("accid") Long accid, @RequestParam("orderNumber") String orderNumber) throws Exception {
        UserAccountDto userAccountDto = userAccountService.userAccountDetail(accid);
        if (userAccountDto == null) {
            throw new WebException(ProductErrorCode.NOT_FOUND_USER_ACCOUNT);
        }
        Map<String, Object> orderDto = orderService.queryOrder(accid, orderNumber);
        if (orderDto == null) {
            throw new WebException(ProductErrorCode.NOT_FOUND_ORDER);
        }
        //根据订单号，拉取微信订单信息
        BigDecimal price = (BigDecimal) orderDto.get("amount");
        String openId = userAccountDto.getOpenId();
        String productName = (String) orderDto.get("productName");
        // 修改商品价格
        Map<String, String> map = WeixinPayUtils.placeOrder(orderNumber, productName, price.multiply(BigDecimal.valueOf(100L)).intValue(), openId, "JSAPI");
        return new Response(map);
    }


}
