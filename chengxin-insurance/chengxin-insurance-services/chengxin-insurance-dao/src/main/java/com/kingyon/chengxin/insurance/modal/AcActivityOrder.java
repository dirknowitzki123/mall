package com.kingyon.chengxin.insurance.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;


public class AcActivityOrder extends BaseEntity {
    /**
     * 商品ID
     * @time 2018-09-18 17:17:05
     */
    private String productId;

    /**
     * 砍价单号
     * @time 2018-09-18 17:17:05
     */
    private String bargainNum;

    /**
     * 市场价
     * @time 2018-09-18 17:17:05
     */

    private Integer marketPrice;

    /**
     * 优惠价
     * @time 2018-09-18 17:17:05
     */
    private Integer price;

    /**
     * 当前被砍后金额
     * @time 2018-09-18 17:17:05
     */
    private Integer currentPrice;

    /**
     * 商名称
     * @time 2018-09-18 17:17:05
     */
    private String productName;

    /**
     * 副名称
     * @time 2018-09-18 17:17:05
     */
    private String productSubName;

    /**
     * 微信openId
     * @time 2018-09-18 17:17:05
     */
    private String openId;

    /**
     * 状态
     * @time 2018-09-18 17:17:05
     */
    private Byte status;

    /**
     * 分享人编码
     * @time 2018-09-18 17:17:05
     */
    private String shareCode;

    /**
     * 活动编码
     * @time 2018-09-18 17:17:05
     */
    private String acCode;

    /**
     * 投保单号
     * @time 2018-09-18 17:17:05
     */
    private String insureNum;

    /**
     * 砍价次数
     * @time 2018-09-18 17:17:05
     */
    private Integer count;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getBargainNum() {
        return bargainNum;
    }

    public void setBargainNum(String bargainNum) {
        this.bargainNum = bargainNum == null ? null : bargainNum.trim();
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductSubName() {
        return productSubName;
    }

    public void setProductSubName(String productSubName) {
        this.productSubName = productSubName == null ? null : productSubName.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode == null ? null : shareCode.trim();
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode == null ? null : acCode.trim();
    }

    public String getInsureNum() {
        return insureNum;
    }

    public void setInsureNum(String insureNum) {
        this.insureNum = insureNum == null ? null : insureNum.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}