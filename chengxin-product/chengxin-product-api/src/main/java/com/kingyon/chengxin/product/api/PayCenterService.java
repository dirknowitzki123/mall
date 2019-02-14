package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.api.BaseService;

import java.util.Map;

public interface PayCenterService extends BaseService {

	/**
	 * 微信回调接口修改状态
	 * @param orderNumber
	 * @return
	 */
	Map<String, String> confirmOrderStatus(String orderNumber, Integer totalFee);
}
