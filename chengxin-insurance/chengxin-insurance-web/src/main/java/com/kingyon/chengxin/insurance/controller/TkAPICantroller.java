package com.kingyon.chengxin.insurance.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.util.Converter;
import com.kingyon.chengxin.insurance.api.TkApiService;
import com.kingyon.chengxin.insurance.dto.qixindto.QxNotify;
import com.kingyon.chengxin.insurance.dto.qxapidto.OrderTrialDto;
import com.kingyon.chengxin.insurance.dto.tkdto.PayUrlDto;
import com.kingyon.chengxin.insurance.util.RequestUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/11/12 0012 10:00
 */
@RestController
@RequestMapping("/api/insurance")
@Slf4j
public class TkAPICantroller {

    @DubboReference
    TkApiService tkApiService;

    @ApiOperation(value="泰康核保接口")
    @PostMapping("/v1/validatePolicy")
    public Response orderTrial(@RequestBody HashMap<String,Object> params) throws Exception {
        Map<String, Object> result = tkApiService.validatePolicy(params);
        return new Response(result);
    };

    @ApiOperation(value="泰康出单接口")
    @PostMapping("/v1/policy")
    public Response policy(@RequestParam("accid")Long accid,@RequestParam("orderNumber") String orderNumber) throws Exception {
        Map<String, Object> policy = tkApiService.policy(accid,orderNumber);
        return new Response(policy);
    };

    @ApiOperation(value="泰康生成支付URL接口")
    @PostMapping("/v1/generatePayUrl")
    public Response generatePayUrl(@ModelAttribute  PayUrlDto payUrlDto) throws Exception {
        Map<String, Object> policy = tkApiService.generatePayUrl(payUrlDto);
        return new Response(policy);
    };



}
