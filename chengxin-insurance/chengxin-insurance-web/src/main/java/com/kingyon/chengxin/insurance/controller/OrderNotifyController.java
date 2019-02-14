package com.kingyon.chengxin.insurance.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.util.Converter;
import com.kingyon.chengxin.insurance.api.QxOrderNotifyService;
import com.kingyon.chengxin.insurance.api.TkApiService;
import com.kingyon.chengxin.insurance.dto.NotifyDto;
import com.kingyon.chengxin.insurance.dto.qixindto.QxData;
import com.kingyon.chengxin.insurance.dto.qixindto.QxNotify;
import com.kingyon.chengxin.insurance.dto.qixindto.QxProduct;
import com.kingyon.chengxin.insurance.util.RequestUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aspen
 * @create: 2018-07-23.
 */


@RestController
@RequestMapping("/serv/insurance")
@Slf4j
public class OrderNotifyController {

    @DubboReference
    private QxOrderNotifyService orderNotifyService;

    @DubboReference
    TkApiService tkApiService;

    @ApiOperation(value="齐欣订单通知")
    @PostMapping("/v1/qxNotify")
    public Map<String, Object> qxNotify(HttpServletRequest request) throws Exception {
        String requestMsg = RequestUtil.readRequestMsg(request);
        log.info("齐欣消息通知:" + requestMsg);

        QxNotify qxNotify = Converter.parseObject(requestMsg, QxNotify.class);
        Boolean aBoolean = orderNotifyService.qxNotify(qxNotify);

        Map<String, Object> result = new HashMap<>();
        if (aBoolean) {
            log.info("处理成功................." + qxNotify.getNotifyType() + "..................");
            result.put("state", true);
        } else {
            log.info("处理失败................." + qxNotify.getNotifyType() + "..................");
            result.put("state", false);
            result.put("failMsg", "保存失败");
        }

        return result;
    };


    @ApiOperation(value="泰康订单通知")
    @PostMapping("/v1/tkNotify")
    public Map<String, Object> tkNotify(HttpServletRequest request) throws Exception {
        String requestMsg = RequestUtil.readRequestMsg(request);
        log.info("泰康消息通知:" + requestMsg);
        Map<String,Object> result = tkApiService.tkNotify(requestMsg);
        return result;
    };

}
