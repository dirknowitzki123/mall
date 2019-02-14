package com.kingyon.chengxin.product.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.product.api.PayCenterService;
import com.kingyon.chengxin.product.web.wxutil.PayCommonUtil;
import com.kingyon.chengxin.product.web.wxutil.PropsValue;
import com.kingyon.chengxin.product.web.wxutil.XMLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2019/1/14 0014.
 */
@Slf4j
@RestController
@RequestMapping("/serv/payment")
public class PayNotifyController {

    @DubboReference
    PayCenterService payCenterService;

    /**
     * 微信支付-异步通知返回接收的方法
     */
    @PostMapping("/wxPay/wxNotifyUrl")
    public String wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> map = xmlToMap(request);

        Map<String, String> retMap = new HashMap<>();
        String return_code = map.get("return_code");
        String result_code = map.get("result_code");
        String return_msg = map.get("return_msg");
        String out_trade_no = map.get("out_trade_no");
        log.info("微信支付结果回调 : {} ", map.toString());
        if (PayCommonUtil.isTenpaySign("UTF-8", map, PropsValue.WECHAT_KEY)) {
            log.info("签名成功............");
            if (!StringUtils.isEmpty(result_code)) {
                if ("SUCCESS".equals(return_code.toUpperCase()) && !StringUtils.isEmpty(result_code)) {
                    //内部订单号，32个字符内

                    log.info("当前系统订单号=================================: " + out_trade_no);
                    if (!StringUtils.isEmpty(out_trade_no)) {
                        //支付成功之后处理逻辑
                        Integer totalFee = Integer.valueOf(map.get("total_fee"));
                        retMap = payCenterService.confirmOrderStatus(out_trade_no, totalFee);
                    } else {
                        retMap.put("return_code", "FAIL");
                        retMap.put("return_msg", "订单编号为空");
                    }
                } else {
                    retMap.put("return_code", "FAIL");
                    retMap.put("return_msg", "result_code为空");
                }
            } else {
                retMap.put("return_code", "FAIL");
            }
        } else {
            log.error("签名失败 订单号 :" + out_trade_no);
            response.getWriter().write("FAIL");
        }
        return XMLUtil.map2Xmlstring(retMap);
    }


    public Map<String, String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = XMLUtil.newDocumentBuilder();
            org.w3c.dom.Document doc = documentBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }

            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }
}
