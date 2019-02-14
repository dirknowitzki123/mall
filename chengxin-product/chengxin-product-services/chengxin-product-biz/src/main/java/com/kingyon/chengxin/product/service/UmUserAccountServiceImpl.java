package com.kingyon.chengxin.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingyon.chengxin.framework.ObjectCopy;
import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.api.UmUserAccountService;
import com.kingyon.chengxin.product.dto.UserAccountDto;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.query.UserAccountQuery;
import com.kingyon.chengxin.product.dto.weixin.ThirdLoginUser;
import com.kingyon.chengxin.product.enums.RegisterFromType;
import com.kingyon.chengxin.product.mapper.OmOrderMapper;
import com.kingyon.chengxin.product.mapper.UmUserAccountMapper;
import com.kingyon.chengxin.product.modal.UmUserAccount;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.rowset.serial.SerialException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2018/11/16 0016.
 */
@DubboService
public class UmUserAccountServiceImpl implements UmUserAccountService {


    @Autowired
    UmUserAccountMapper userAccountMapper;

    @Autowired
    OmOrderMapper orderMapper;

    @Override
    public UserAccountDto selectByOpenId(String openId) throws Exception {
        UmUserAccount umUserAccount = userAccountMapper.selectByOpenId(openId);
        return ObjectCopy.copyTo(umUserAccount, UserAccountDto.class);
    }

    @Override
    public UserAccountDto selectByPrimaryKey(Long id) throws Exception {
        UmUserAccount umUserAccount = userAccountMapper.selectByPrimaryKey(id);
        return ObjectCopy.copyTo(umUserAccount, UserAccountDto.class);
    }

    @Override
    public PageDto userAccountList(UserAccountQuery query) {
        PageHelper.startPage(query.getPageIndex(), query.getPageSize());
        List<Map<String, Object>> orderList = userAccountMapper.selectUserAccountList(query);
        PageInfo pageInfo = new PageInfo(orderList);
        PageDto pageDto = new PageDto();
        pageDto.setDataCount(Integer.parseInt(pageInfo.getTotal() + ""));
        pageDto.setPageIndex(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setData(orderList);
        return pageDto;
    }

    @Override
    public UserAccountDto userAccountDetail(Long id) throws Exception {
        UmUserAccount umUserAccount = userAccountMapper.selectByPrimaryKey(id);
        if (umUserAccount == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_USER_ACCOUNT);
        }
        UserAccountDto userAccountDto = ObjectCopy.copyTo(umUserAccount, UserAccountDto.class);
        userAccountDto.setCreateTime(umUserAccount.getCreateTime());
        return userAccountDto;
    }

    @Override
    public Boolean userAccountRemark(Long id, String remark) {
        UmUserAccount umUserAccount = userAccountMapper.selectByPrimaryKey(id);
        if (umUserAccount == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_USER_ACCOUNT);
        }
        umUserAccount.setRemark(remark);
        int i = userAccountMapper.updateByPrimaryKeySelective(umUserAccount);
        return i > 0;
    }

    @Override
    public UserAccountDto login(String openId, boolean flag) throws Exception {
        UmUserAccount umUserAccount = userAccountMapper.selectByOpenId(openId);
        if (umUserAccount == null) {
            return null;
        }
        return ObjectCopy.copyTo(umUserAccount, UserAccountDto.class);
    }

    @Override
    public UserAccountDto thirdLoginCreateUser(ThirdLoginUser wechatUser, RegisterFromType wechat) throws Exception {
        UmUserAccount userAccount = ObjectCopy.copyTo(wechatUser, UmUserAccount.class);
        userAccount.setSources(wechat.getDisplay());
        userAccount.setSourceWay(wechat.getDisplay());
        userAccount.setHeadUrl(wechatUser.getHead());
        int insert = userAccountMapper.insert(userAccount);
        if (insert > 0) {
            UserAccountDto dto = ObjectCopy.copyTo(userAccount, UserAccountDto.class);
            dto.setId(userAccount.getId());
            return dto;
        }
        return null;
    }
}
