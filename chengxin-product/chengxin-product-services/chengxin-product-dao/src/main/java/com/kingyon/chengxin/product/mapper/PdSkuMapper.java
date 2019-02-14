package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.dto.SkuDto;
import com.kingyon.chengxin.product.modal.PdSku;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@Mapper
@ImportResource("/mappers/product/PdSkuMapper.xml")
public interface PdSkuMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PdSku record);

    int insertSelective(PdSku record);

    PdSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PdSku record);

    int updateByPrimaryKey(PdSku record);

    /**
     * 逻辑删除商品的SKU属性
     *
     * @param productId
     */
    @Update("update pd_sku set deleted = -1  where product_id = #{productId}")
    void logicDelByProductId(@Param("productId") Long productId);

    /**
     * 获取商品的SKU属性
     *
     * @param productId
     * @return
     */
    List<PdSku> selectByProductId(@Param("productId") Long productId,@Param("status") Byte status);

    /**
     * 改变商品SKU属性状态
     * @param productId
     * @param skuId
     * @return
     */
    @Update("UPDATE pd_sku SET status = CASE status WHEN 1 THEN 0 ELSE 1 END WHERE product_id= #{productId} and id = #{skuId}")
    int changeSkuStatus(@Param("productId") Long productId,@Param("skuId") Long skuId);

    /**
     * 修改SKU库存
     * @param skuId
     * @return
     */
    @Update("update pd_sku set stock = stock - 1, sale_count = sale_count + 1 WHERE id = #{skuId}")
    int updateSkuStock(@Param("skuId") Long skuId);
}