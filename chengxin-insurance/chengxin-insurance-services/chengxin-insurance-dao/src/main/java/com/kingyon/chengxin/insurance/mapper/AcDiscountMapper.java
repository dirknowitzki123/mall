package com.kingyon.chengxin.insurance.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.insurance.modal.AcDiscount;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.ImportResource;

@ImportResource("/mappers/insurance/AcDiscountMapper.xml")
public interface AcDiscountMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AcDiscount record);

    int insertSelective(AcDiscount record);

    AcDiscount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcDiscount record);

    int updateByPrimaryKey(AcDiscount record);

    AcDiscount selectByProductId(@Param("productId") Long productId);
}