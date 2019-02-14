package com.kingyon.chengxin.insurance.api;

import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.insurance.dto.qxapidto.*;
import com.kingyon.chengxin.insurance.dto.qixindto.QxData;
import com.kingyon.chengxin.insurance.dto.qixindto.QxProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QxApiService extends BaseService {

    /**
     * 获取保单详情
     *
     * @param insureNum
     * @return
     * @throws Exception
     */
    public QxData policyDetail(String insureNum) throws Exception;

    /**
     * 保险商品列表
     *
     * @return
     * @throws Exception
     */
    public List<QxProduct> productList() throws Exception;

    /**
     * 获取签名信息
     *
     * @return
     * @throws Exception
     */
    public String getProductDetailUrl(String partnerUniqKey, String caseCode, String paySuccessUrl) throws Exception;

    /**
     * 默认试算
     *
     * @param caseCode
     * @return
     * @throws Exception
     */
    public Object defaultTrial(String caseCode) throws Exception;

    /**
     * 试算
     *
     * @param orderTrialDto
     * @return
     * @throws Exception
     */
    public Object orderTrial(OrderTrialDto orderTrialDto) throws Exception;

    /**
     * 折扣活动 投保
     *
     * @param insureReq
     * @return
     * @throws Exception
     */
    public String insure(InsureReq insureReq) throws Exception;


    /**
     * 提交投保
     *
     * @param insureReq
     * @return
     * @throws Exception
     */
    public Map<String, Object> insureCommit(InsureReq insureReq) throws Exception;

    /**
     * 获取产品可投保省市地区（二级和三级地区需根据上一级地区编码再次调用此接口获得）
     *
     * @param caseCode
     * @param areaCode
     * @return
     * @throws Exception
     */
    public HashMap<String,Object> productInsuredArea(String caseCode, String areaCode) throws Exception;

    /**
     * 获取产品职业信息
     *
     * @param caseCode
     * @return
     * @throws Exception
     */

    public Object productInsuredJob(String caseCode) throws Exception;

    /**
     * 获取指定产品详细信息，包含产品条款、保障项目等信息
     *
     * @param caseCode
     * @return
     * @throws Exception
     */
    public Object productDetail(String caseCode) throws Exception;

    /**
     * 获取产品健康告知题目信息（需根据试算因子获取对应健康告知题目）
     *
     * @param healthStatementReq
     * @return
     * @throws Exception
     */
    public Object healthStatement(HealthStatementReq healthStatementReq) throws Exception;

    /**
     * 根据产品查询产品投保属性信息，包含投保参数、校验规则以及约束信息
     *
     * @param productId
     * @param partnerUniqKey
     * @param shareCode
     * @return
     */
    public Object productInsuredAttr(Long productId, String partnerUniqKey, String shareCode) throws Exception;

    /**
     * 根据产品查询产品投保属性信息，包含投保参数、校验规则以及约束信息
     *
     * @return
     */
    public Object productInsuredAttr(String caseCode) throws Exception;

    /**
     * 用于活动支付
     *
     * @param bargainNum
     * @return
     */
    public Object localPay(String bargainNum) throws Exception;


    /**
     * 非实时结算方式在投保成功后需调用此接口确认支付进行出单
     *
     * @param orderNumber
     * @return
     */
    public Object localOrderPay(Long accid,String orderNumber) throws Exception;

    /**
     * 提交用户告知答案，获取承保告知id
     *
     * @param submitHealthStateReq
     * @return
     */
    public Object submitHealthState(SubmitHealthStateReq submitHealthStateReq) throws Exception;

    Object downloadUrl(String insureNum) throws Exception;
}
