package com.kingyon.chengxin.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Auther: Aspen
 * @Date: 2018/10/16 0016 09:04
 */
@Data
public class ProductDocumentDto extends BaseDto {


    private Long id;
    /**
     * 文档名称
     *
     * @time 2018-10-15 11:39:25
     */
    @NotNull(message = "文档名称不能为空")
    private String docName;

    /**
     * 文档类型
     *
     * @time 2018-10-15 11:39:25
     */
    private String docType;

    /**
     * 文档摘要
     *
     * @time 2018-10-15 11:39:25
     */
    private String docDigest;

    private Long operator;

    /**
     * 文档访问地址
     *
     * @time 2018-10-15 11:39:25
     */
    private String docUrl;

    /**
     * 产品ID
     *
     * @time 2018-10-15 11:39:25
     */
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    /**
     * 购买前告知 1 :为是 0 为否
     * @time 2018-10-22 16:29:45
     */
    private Byte buyTold;


}
