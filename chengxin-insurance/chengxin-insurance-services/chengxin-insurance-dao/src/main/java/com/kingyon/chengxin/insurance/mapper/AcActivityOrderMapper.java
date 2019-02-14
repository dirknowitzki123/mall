package com.kingyon.chengxin.insurance.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.insurance.modal.AcActivityOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ImportResource;

@ImportResource("/mappers/insurance/AcActivityOrderMapper.xml")
public interface AcActivityOrderMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AcActivityOrder record);

    int insertSelective(AcActivityOrder record);

    AcActivityOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcActivityOrder record);

    int updateByPrimaryKey(AcActivityOrder record);

    @Select("select count(0) from ac_activity_order  where product_id = #{productId} and open_id =#{openId} and status=#{status}")
    int selectByOpenIdAndProid(@Param("productId") String productId,@Param("openId") String openId,@Param("status") Byte status );

    AcActivityOrder selectByBargainNum(@Param("bargainNum") String bargainNum);
}