package com.kingyon.chengxin.insurance.api;

import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.insurance.dto.tkdto.PayUrlDto;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2018/11/12 0012.
 */
public interface TkApiService extends BaseService {
    /**
     * 泰康核保接口
     * @param params
     * @return
     * @throws Exception
     */
    Map<String, Object> validatePolicy(Map<String,Object> params) throws Exception;

    /**
     * 支付完成后出单
     * @param accid
     * @param orderNumber
     * @return
     */
    Map<String,Object> policy(Long accid,String orderNumber);

    /**
     * 获取泰康支付UTL
     * @param payUrlDto
     * @return
     */

    Map<String,Object> generatePayUrl(PayUrlDto payUrlDto );

    /**
     * 泰康支付成功回调接口
     * @param requestMsg
     * @return
     */
    Map<String,Object> tkNotify(String requestMsg);

}
