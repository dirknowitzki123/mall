package com.kingyon.chengxin.product.dto.weixin;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class WechatDto extends BaseDto {

    /**
     * 授权用户唯一标识
     */
    private String openID;

    /**
     * 网页授权接口调用凭证
     */

    private String accessToken;
    /**
     * 凭证有效时长
     */
    private int expiresIn;
    /**
     * 用于刷新凭证
     */
    private String refreshToken;

    /**
     * 用户授权作用域
     */
    private String scope;



}
