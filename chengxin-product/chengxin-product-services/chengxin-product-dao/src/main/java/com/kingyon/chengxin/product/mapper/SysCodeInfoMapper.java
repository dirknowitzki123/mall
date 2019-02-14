package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.SysCodeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ImportResource;

import java.util.List;
import java.util.Map;

@Mapper
@ImportResource("/mappers/product/SysCodeInfoMapper.xml")
public interface SysCodeInfoMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysCodeInfo record);

    int insertSelective(SysCodeInfo record);

    SysCodeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysCodeInfo record);

    int updateByPrimaryKey(SysCodeInfo record);

    @Select("select id as brandId,code_name as brandName from sys_code_info WHERE code_type = 'brand_code' AND deleted = 0")
    List<Map<String, Object>> selectBrandCodeList();

    @Select("select count(0) from sys_code_info where code_name = #{brandName} and  deleted = 0 and code_type = 'brand_code'")
    int selectByName(@Param("brandName") String brandName);
}