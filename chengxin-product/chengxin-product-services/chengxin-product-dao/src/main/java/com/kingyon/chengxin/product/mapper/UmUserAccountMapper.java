package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.dto.query.UserAccountQuery;
import com.kingyon.chengxin.product.modal.UmUserAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.ImportResource;

import java.util.List;
import java.util.Map;

@ImportResource("/mappers/product/UmUserAccountMapper.xml")
public interface UmUserAccountMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UmUserAccount record);

    int insertSelective(UmUserAccount record);

    UmUserAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmUserAccount record);

    int updateByPrimaryKey(UmUserAccount record);

    UmUserAccount selectByOpenId(@Param("openId") String openId);

    List<Map<String,Object>> selectUserAccountList(UserAccountQuery query);



}
