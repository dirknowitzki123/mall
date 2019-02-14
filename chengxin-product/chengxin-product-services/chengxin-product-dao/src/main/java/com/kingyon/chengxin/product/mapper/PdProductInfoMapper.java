package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.dto.query.ProductInfoQuery;
import com.kingyon.chengxin.product.dto.response.ProductListDto;
import com.kingyon.chengxin.product.modal.PdProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@Mapper
@ImportResource("/mappers/product/PdProductInfoMapper.xml")
public interface PdProductInfoMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PdProductInfo record);

    int insertSelective(PdProductInfo record);

    PdProductInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PdProductInfo record);

    int updateByPrimaryKeyWithBLOBs(PdProductInfo record);

    int updateByPrimaryKey(PdProductInfo record);

    List<ProductListDto> selectProductListBoss(ProductInfoQuery infoQuery);

    List<PdProductInfo> selectProductList(ProductInfoQuery query);

    @Select("select product_code FROM pd_product_info WHERE pro_type = '10001'")
    List<String> productCodeList();

}