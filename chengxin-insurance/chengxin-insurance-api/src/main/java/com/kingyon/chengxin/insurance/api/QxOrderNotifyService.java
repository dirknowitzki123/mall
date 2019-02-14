package com.kingyon.chengxin.insurance.api;

import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.insurance.dto.qixindto.QxData;
import com.kingyon.chengxin.insurance.dto.qixindto.QxNotify;
import com.kingyon.chengxin.insurance.dto.qixindto.QxProduct;

import java.util.List;

/**
 * @Author: Aspen
 * @create: 2018-07-23.
 */
public interface QxOrderNotifyService extends BaseService {
    /**
     * 齐欣 保单通知
     *
     * @param notify
     * @return
     * @throws Exception
     */
    public Boolean qxNotify(QxNotify notify) throws Exception;


}
