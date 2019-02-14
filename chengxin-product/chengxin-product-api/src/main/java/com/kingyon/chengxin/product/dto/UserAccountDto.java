package com.kingyon.chengxin.product.dto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2018/11/16 0016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto extends BaseDto {
    private Long id;
    /**
     * 昵称
     *
     * @time 2018-09-18 11:05:29
     */
    private String nickName;
    /**
     * 账号
     *
     * @time 2018-09-18 11:05:29
     */
    private String accountName;
    /**
     * 姓名
     *
     * @time 2018-09-18 11:05:29
     */
    private String userName;

    /**
     * 身份证号
     *
     * @time 2018-09-18 11:05:29
     */
    private String idcard;

    /**
     * 性别
     *
     * @time 2018-09-18 11:05:29
     */
    private Integer sex;


    /**
     * 来源渠道
     *
     * @time 2018-09-18 11:05:29
     */
    private String sources;

    /**
     * 来源方式
     *
     * @time 2018-09-18 11:05:29
     */
    private String sourceWay;
    /**
     * 微信公众号上的openid
     * @time 2018-09-18 11:05:29
     */
    private String openId;

    /**
     * 备注
     *
     * @time 2018-09-18 11:05:29
     */
    private String remark;
    private String phone;
    private Date createTime;
    private String headUrl;

//    private List<Map<String,Object>>  userOrderList = new ArrayList<>();

}
