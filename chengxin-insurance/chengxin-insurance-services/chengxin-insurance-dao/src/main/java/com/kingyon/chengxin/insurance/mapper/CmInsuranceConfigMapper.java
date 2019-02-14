package com.kingyon.chengxin.insurance.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.insurance.modal.CmInsuranceConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.ImportResource;

@ImportResource("/mappers/insurance/CmInsuranceConfigMapper.xml")
public interface CmInsuranceConfigMapper extends BaseMapper {

    CmInsuranceConfig selectByPrimaryKey(Long id);
    CmInsuranceConfig selectPutawayByProductId(@Param("productId") Long productId);
}