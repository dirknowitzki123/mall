package com.kingyon.chengxin.product.dto.weixin;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ThirdLoginUser extends BaseDto {
    /**
     * 用户标识
     */
    private String openId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像链接
     */
    private String head;
    /**
     * 性别（1是男性，2是女性，0是未知）
     */
    private Integer sex;
    /**
     * 网页授权接口调用凭证
     */
    private String accessToken;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;


}
