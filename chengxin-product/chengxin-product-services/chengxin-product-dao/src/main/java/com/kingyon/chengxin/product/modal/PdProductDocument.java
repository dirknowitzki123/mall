package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

public class PdProductDocument extends BaseEntity {
    /**
     * 文档名称
     * @time 2018-10-22 16:29:45
     */
    private String docName;

    /**
     * 文档类型
     * @time 2018-10-22 16:29:45
     */
    private String docType;

    private Long operator;



    /**
     * 文档访问地址
     * @time 2018-10-22 16:29:45
     */
    private String docUrl;

    /**
     * 产品ID
     * @time 2018-10-22 16:29:45
     */
    private Long productId;

    /**
     * 购买前告知 0 :为是 1 为否
     * @time 2018-10-22 16:29:45
     */
    private Byte buyTold;

    /**
     * 文档摘要
     * @time 2018-10-22 16:29:45
     */
    private String docDigest;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName == null ? null : docName.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }



    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl == null ? null : docUrl.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Byte getBuyTold() {
        return buyTold;
    }

    public void setBuyTold(Byte buyTold) {
        this.buyTold = buyTold;
    }

    public String getDocDigest() {
        return docDigest;
    }

    public void setDocDigest(String docDigest) {
        this.docDigest = docDigest == null ? null : docDigest.trim();
    }
}