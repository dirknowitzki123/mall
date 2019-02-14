package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.UmUserReceivingAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UmUserReceivingAddressMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UmUserReceivingAddress record);

    int insertSelective(UmUserReceivingAddress record);

    UmUserReceivingAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmUserReceivingAddress record);

    int updateByPrimaryKey(UmUserReceivingAddress record);

    @Select("select phone,consignee,detail_address,default_address from um_user_receiving_address where account_id = #{accountId}")
    List<Map<String, Object>> selectAddressList(@Param("accountId") Long accountId);

    @Select("select phone,consignee,detail_address,default_address from um_user_receiving_address where account_id = #{accountId} and default_address = #{defaultAddress}")
    Map<String, Object> defaultReceiveAddress(@Param("accountId") Long accountId, @Param("defaultAddress") Integer defaultAddress);

    @Select("select id, account_id, phone,consignee,detail_address from um_user_receiving_address where account_id = #{accountId} " +
            "and phone = #{phone} and consignee = #{consignee} and detail_address = #{detail_address}")
    UmUserReceivingAddress selectReceiveAddress(@Param("accountId") Long accountId, @Param("phone") String phone,
                                             @Param("consignee") String consignee, @Param("detail_address") String detail_address);

    @Select("select id, account_id,phone,consignee,detail_address,default_address from um_user_receiving_address where id != #{id} " +
            "and account_id = #{accountId}")
    List<UmUserReceivingAddress> selectUndefaultAddress(@Param("id") Long id, @Param("accountId") Long accountId);
}