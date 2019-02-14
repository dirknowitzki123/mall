package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.PdProductDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@Mapper
@ImportResource("/mappers/product/PdProductDocumentMapper.xml")
public interface PdProductDocumentMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PdProductDocument record);

    int insertSelective(PdProductDocument record);

    PdProductDocument selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PdProductDocument record);

    int updateByPrimaryKey(PdProductDocument record);

    @Update("update pd_product_document set deleted = -1 where product_id =#{id} and operator=9999")
    int logicDeleteByProductId(Long id);

    int insertBatch(List<PdProductDocument> documentList);

    List<PdProductDocument> productDocListByProductId(Long productId);

    PdProductDocument checkBuyToldByProductId(@Param("productId") Long productId,@Param("flag") Integer flag);
}