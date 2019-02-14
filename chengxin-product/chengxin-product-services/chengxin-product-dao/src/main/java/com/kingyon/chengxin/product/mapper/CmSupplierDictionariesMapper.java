package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.CmSupplierDictionaries;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@Mapper
@ImportResource("/mappers/product/CmSupplierDictionariesMapper.xml")
public interface CmSupplierDictionariesMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CmSupplierDictionaries record);

    int insertSelective(CmSupplierDictionaries record);

    CmSupplierDictionaries selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CmSupplierDictionaries record);

    int updateByPrimaryKey(CmSupplierDictionaries record);

    CmSupplierDictionaries getOneByName(@Param("name") String name);

    @Select("select id as id,supplier_name as supplierName from cm_supplier_dictionaries")
    List<CmSupplierDictionaries> getAll();

}