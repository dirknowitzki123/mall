package com.kingyon.chengxin.insurance.controller;


import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.insurance.api.QxApiService;
import com.kingyon.chengxin.insurance.dto.qxapidto.HealthStatementReq;
import com.kingyon.chengxin.insurance.dto.qxapidto.InsureReq;
import com.kingyon.chengxin.insurance.dto.qxapidto.OrderTrialDto;
import com.kingyon.chengxin.insurance.dto.qixindto.QxData;
import com.kingyon.chengxin.insurance.dto.qixindto.QxProduct;
import com.kingyon.chengxin.insurance.dto.qxapidto.SubmitHealthStateReq;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insurance")
@Slf4j
public class QxAPIController {

    @DubboReference
    private QxApiService apiService;

    @ApiOperation(value="查看保单详情")
    @PostMapping("/v1/getPolicyDetail")
    public Response getPolicyDetail(@RequestParam("insureNum") String insureNum) throws Exception {
        QxData qxData = apiService.policyDetail(insureNum);
        return new Response(qxData);
    };

    @ApiOperation(value="齐欣产品列表")
    @GetMapping("/v1/productList")
    public Response productList() throws Exception {
        List<QxProduct> qxProducts = apiService.productList();
        return new Response(qxProducts);
    };

    @ApiOperation(value="获取跳转产品详情的url")
    @PostMapping("/v1/getProductDetailUrl")
    public Response getProductDetailUrl(@RequestParam String partnerUniqKey, @RequestParam String caseCode,@RequestParam(required = false) String paySuccessUrl) throws Exception {
        String sign = apiService.getProductDetailUrl(partnerUniqKey, caseCode,paySuccessUrl);
        return new Response(sign);
        
    };

    @ApiOperation(value="默认试算")
    @GetMapping("/v1/defaultTrial")
    public Response defaultTrial(@RequestParam String caseCode) throws Exception {
        Object trial = apiService.defaultTrial(caseCode);
        return new Response(trial);
    };


    @ApiOperation(value="试算")
    @PostMapping("/v1/orderTrial")
    public Response orderTrial(@RequestBody OrderTrialDto orderTrialDto) throws Exception {
        log.info(orderTrialDto.toString());

        Object trial = apiService.orderTrial(orderTrialDto);
        return new Response(trial);
    };

    @ApiOperation(value="提交用户信息进行承保下单")
    @PostMapping("/v1/insure")
    public Response insure(@RequestBody @Validated InsureReq insureReq) throws Exception {
        log.info(insureReq.toString());
        String insure = apiService.insure(insureReq);
        return new Response(insure);
    };

    @ApiOperation(value="承保下单")
    @PostMapping("/v1/insureCommit")
    public Response insureCommit(@RequestBody @Validated InsureReq insureReq, HttpServletRequest request) throws Exception {
        Map<String, Object> insureCommit = apiService.insureCommit(insureReq);
        return new Response(insureCommit);
    };

    @ApiOperation(value="居住省市")
    @PostMapping("/v1/productInsuredArea")
    public Response productInsuredArea(@RequestParam("caseCode") String caseCode, @RequestParam(name="areaCode",required=false) String areaCode) throws Exception {
        Object insuredArea = apiService.productInsuredArea(caseCode, areaCode);
        return new Response(insuredArea);
    };

    @ApiOperation(value="职业信息")
    @PostMapping("/v1/productInsuredJob")
    public Response productInsuredJob(@RequestParam String caseCode) throws Exception {
        Object insuredJob = apiService.productInsuredJob(caseCode);
        return new Response(insuredJob);
    };

    @ApiOperation(value="产品详情")
    @PostMapping("/v1/productDetail")
    public Response productDetail(@RequestParam String caseCode) throws Exception {
        Object insuredJob = apiService.productDetail(caseCode);
        return new Response(insuredJob);
    };

     @ApiOperation(value="获取健康告知")
    @PostMapping("/v1/healthStatement")
    public Response healthStatement(@RequestBody HealthStatementReq healthStatementReq) throws Exception {
        Object healthStatement = apiService.healthStatement(healthStatementReq);
        return new Response(healthStatement);
    };

    @ApiOperation(value="提交健康告知")
    @PostMapping("/v1/submitHealthState")
    public Response submitHealthState(@RequestBody SubmitHealthStateReq submitHealthStateReq) throws Exception {
        Object submitHealthState = apiService.submitHealthState(submitHealthStateReq);
        return new Response(submitHealthState);
    };


    @ApiOperation(value="投保属性")
    @PostMapping("/v1/productInsuredAttr")
    public Response productInsuredAttr(@RequestParam Long productId,@RequestParam String partnerUniqKey,@RequestParam(required = false) String shareCode) throws Exception {
        Object healthStatement = apiService.productInsuredAttr(productId,partnerUniqKey,shareCode);
        return new Response(healthStatement);
    };

    @ApiOperation(value="本地支付")
    @PostMapping("/v1/localPay")
    public Response localPay(@RequestParam String bargainNum) throws Exception {
        Object localPayRes = apiService.localPay(bargainNum);
        return new Response(localPayRes);
    };

    @ApiOperation(value="订单支付完成后掉此接口")
    @PostMapping("/v1/localOrderPay")
    public Response localOrderPay(@RequestParam("accid")Long accid,@RequestParam("orderNumber") String orderNumber) throws Exception {
        log.info("------------------------"+accid+" : -------"+orderNumber+"--------------------------------------------");
        Object localPayRes = apiService.localOrderPay(accid,orderNumber);
        return new Response(localPayRes);
    };

    @ApiOperation(value="获取保单文件下载地址")
    @PostMapping("/v1/downloadUrl")
    public Response downloadUrl(@RequestParam("insureNum") String insureNum) throws Exception {
        Object downloadUrl = apiService.downloadUrl(insureNum);
        return new Response(downloadUrl);
    };



}
