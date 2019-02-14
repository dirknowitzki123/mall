package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.product.dto.UserAccountDto;
import com.kingyon.chengxin.product.dto.query.UserAccountQuery;
import com.kingyon.chengxin.product.dto.weixin.ThirdLoginUser;
import com.kingyon.chengxin.product.enums.RegisterFromType;

/**
 * @Auther: Aspen
 * @Created: 2018/11/16 0016.
 */
public interface UmUserAccountService extends BaseService {
    /**
     * 通过微信openID 获取用户信息
     * @param openId
     * @return
     * @throws Exception
     */
    UserAccountDto selectByOpenId(String openId) throws Exception;

    /**
     * 通过主键ID 获取用户信息
     * @param id
     * @return
     * @throws Exception
     */
    UserAccountDto selectByPrimaryKey(Long id) throws Exception;

    /**
     * 查询用户列表Boss
     * @param query
     * @return
     */
    PageDto userAccountList(UserAccountQuery query);

    /**
     * 通过用户ID查询用户详情
     * @param id
     * @return
     * @throws Exception
     */
    UserAccountDto userAccountDetail(Long id) throws Exception;

    /**
     * 后台用户备注
     * @param id
     * @param remark
     * @return
     */
    Boolean userAccountRemark(Long id, String remark);

    UserAccountDto login(String openId, boolean flag) throws Exception;

    UserAccountDto thirdLoginCreateUser(ThirdLoginUser wechatUser, RegisterFromType wechat) throws Exception;
}
