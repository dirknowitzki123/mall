package com.kingyon.chengxin.product.enums;

/**
 * @Auther: Aspen
 * @Date: 2018/10/16 0016 09:46
 */
public enum DocumentType {

    RICH_TEXT(2340001,"富文本"),
    UPLOAD_DOC(2340002,"上传文档");

    private Integer code;
    private String  message;

    DocumentType(Integer code, String message) {
        this.code =code;
        this.message= message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
