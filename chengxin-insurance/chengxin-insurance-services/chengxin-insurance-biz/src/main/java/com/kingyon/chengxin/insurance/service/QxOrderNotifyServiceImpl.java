package com.kingyon.chengxin.insurance.service;

import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.net.OkHttpRest;
import com.kingyon.chengxin.framework.util.Converter;
import com.kingyon.chengxin.framework.util.MD5Util;
import com.kingyon.chengxin.insurance.api.QxApiService;
import com.kingyon.chengxin.insurance.api.QxOrderNotifyService;
import com.kingyon.chengxin.insurance.dto.qixindto.QxData;
import com.kingyon.chengxin.insurance.dto.qixindto.QxNotify;
import com.kingyon.chengxin.insurance.dto.qixindto.QxNotifyData;
import com.kingyon.chengxin.insurance.dto.qixindto.QxPolicys;
import com.kingyon.chengxin.insurance.mapper.QxOrderNotifyMapper;
import com.kingyon.chengxin.insurance.mapper.QxPolicyInfoMapper;
import com.kingyon.chengxin.insurance.modal.QxOrderNotify;
import com.kingyon.chengxin.insurance.modal.QxPolicyInfo;
import com.kingyon.chengxin.product.api.UmUserAccountService;
import com.kingyon.chengxin.product.dto.UserAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aspen
 * @create: 2018-07-23.
 */
@DubboService
@Slf4j
public class QxOrderNotifyServiceImpl implements QxOrderNotifyService {

    @Value("${apps.common.qixin.key}")
    private String KEY;


    @Value("${apps.common.waiKuaiXiaUrl_Pub}")
    private String pub_url;

    @Value("${apps.common.waiKuaiXiaUrl_Test}")
    private String test_url;

    @Value("${apps.common.openTest}")
    private String openTest;

    @Value("${apps.common.bigDataPolicyDetailUrl}")
    private String bigDataPolicyDetailUrl;


    private static OkHttpRest okHttpRest = OkHttpRest.getHttpClient("qx_client");

    static {
        okHttpRest.addHeader("Content-Type", "application/json;charset=UTF-8");
        okHttpRest.addHeader("Accept", "application/json");
    }

    @Autowired
    private QxOrderNotifyMapper orderNotifyMapper;

    @Autowired
    private QxPolicyInfoMapper policyInfoMapper;

    @Autowired
    private QxApiService qxApiService;

    @DubboReference
    UmUserAccountService userAccountService;

    @Override
    public Boolean qxNotify(QxNotify qxNotify) throws Exception {
        int flag = 0;
        QxNotifyData data = qxNotify.getData();
        String signStr = KEY + Converter.toJson(data, false, true);
        int notifyType = qxNotify.getNotifyType();
        UserAccountDto userAccountDto = userAccountService.selectByOpenId(data.getPartnerUniqueKey());
        Long accid;
        if (userAccountDto != null) {
            //加1000 表示为名健康的用户
            accid = 1000+userAccountDto.getId();
        } else {
            accid = Long.valueOf(data.getPartnerUniqueKey());
        }
        //记录日志
        QxOrderLogger.notifyLog(accid, data.getInsureNum(), notifyType, data.getState(), qxNotify.toString());

        log.info("通知类型 :" + notifyType + " - signStr : " + signStr);
        String checkSign = MD5Util.MD5Encode(signStr, "UTF-8");
        if (qxNotify.getSign().equals(checkSign)) {
            log.info("验证签名成功!........");
            QxOrderNotify checkNotify = orderNotifyMapper.selectByInsureNum(data.getInsureNum());
            QxOrderNotify notify = data.copyTo(QxOrderNotify.class);
            notify.setAccid(accid);
            notify.setNotifyType(notifyType);
            if (checkNotify == null) {
                flag = orderNotifyMapper.insert(notify);
            } else {
                if (notifyType == 2 || notifyType == 3 || notifyType == 4) {
                    notifyWKX(data,notifyType);
                }
                if (checkNotify.getNotifyType() != notifyType && checkNotify.getNotifyType() < notifyType) {
                    notify.setId(checkNotify.getId());
                    notify.setModifyTime(new Date());
                    flag = orderNotifyMapper.updateByPrimaryKeySelective(notify);
                    List<QxPolicys> policys = data.getPolicys();
                    if (null != policys && !policys.isEmpty()) {
                        for (QxPolicys policy : policys) {
                            QxPolicyInfo qxPolicyInfo = policy.copyTo(QxPolicyInfo.class);
                            qxPolicyInfo.setInsureNum(checkNotify.getInsureNum());
                            policyInfoMapper.insert(qxPolicyInfo);
                        }
                    }
                } else {
                    return true;
                }
            }
        } else {
            log.error("验证签名失败!........");
        }

        return flag > 0;
    }

    private void notifyWKX(QxNotifyData data,Integer notifyType)  {

        try {
            String mobile = "";
            String cardNumber="";
            QxData qxData = null;
            log.info("-------------------------------------mobile:" + data.getPartnerUniqueKey() + "--------------------------------");
            qxData = qxApiService.policyDetail(data.getInsureNum());
            if (null != qxData) {
                //向大数据那边推送保单详情
                pushBigData(Converter.toJson(qxData));

                Map orderDetail = (HashMap) qxData.getOrderDetail();
                Map applicant = (HashMap) orderDetail.get("applicant");
                mobile = (String) applicant.get("mobile");
                cardNumber = (String) applicant.get("cardNumber");
            }
            HashMap param = new HashMap();
            param.put("invitePhoneNo", data.getPartnerUniqueKey());
            param.put("phoneNo", mobile);
            param.put("prodNo", data.getCaseCode());
            param.put("insuranceAmt", data.getPrice() + "");
            param.put("insuStatus", (notifyType == 2 || notifyType == 3) ? "49900001" : "49900003");
            param.put("insuPolicyNo", data.getInsureNum());
            param.put("certNo", cardNumber);

            HashMap params = new HashMap();
            params.put("params", param);
            String paramsJson = Converter.toJson(params, false, true);
            System.out.println(paramsJson);
            String url = "true".equals(openTest) ? test_url : pub_url;
            String result = okHttpRest.doPost(url, paramsJson, String.class);
            log.info("外快侠返回状态 : {}", result);
        } catch (Exception e) {
            log.error("推送外快侠报错:{}",e);
        }

    };


    private void  pushBigData(String jsonData){
        String result = okHttpRest.doPost(bigDataPolicyDetailUrl, jsonData, String.class);
        log.info("推送大数据返回信息 :{} ",result);
    }



}
