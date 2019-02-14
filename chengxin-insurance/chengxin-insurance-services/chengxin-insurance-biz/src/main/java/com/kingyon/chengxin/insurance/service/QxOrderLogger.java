package com.kingyon.chengxin.insurance.service;

import com.kingyon.chengxin.insurance.mapper.QxOrderLogMapper;
import com.kingyon.chengxin.insurance.modal.QxOrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QxOrderLogger {

    private static QxOrderLogMapper logMapper;

    @Autowired
    public void setLogMapper(QxOrderLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    public static void notifyLog(Long accid, String insureNum, int notifyType, Boolean state, String notifyData) {
        QxOrderLog qxOrderLog = new QxOrderLog();
        qxOrderLog.setAccid(accid);
        qxOrderLog.setInsureNum(insureNum);
        qxOrderLog.setNotifyType(notifyType);
        qxOrderLog.setState(state);
        qxOrderLog.setNotifyData(notifyData);
        logMapper.insert(qxOrderLog);
    }

}
