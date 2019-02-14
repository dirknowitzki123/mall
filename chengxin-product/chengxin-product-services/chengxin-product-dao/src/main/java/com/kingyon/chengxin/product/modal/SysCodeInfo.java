package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

public class SysCodeInfo extends BaseEntity {
    /**
     * 码值类型
     * @time 2019-01-04 14:55:07
     */
    private String codeType;

    /**
     * 码值名称
     * @time 2019-01-04 14:55:07
     */
    private String codeName;



    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

}