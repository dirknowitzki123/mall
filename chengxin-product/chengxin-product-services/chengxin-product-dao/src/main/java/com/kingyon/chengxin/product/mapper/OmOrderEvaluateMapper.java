package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.OmOrderEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@Mapper
@ImportResource("/mappers/product/OmOrderEvaluateMapper.xml")
public interface OmOrderEvaluateMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OmOrderEvaluate record);

    int insertSelective(OmOrderEvaluate record);

    OmOrderEvaluate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmOrderEvaluate record);

    int updateByPrimaryKey(OmOrderEvaluate record);

    List<OmOrderEvaluate> evaluateList(OmOrderEvaluate orderEvaluate);

    @Select("select count(0) from om_order_evaluate  WHERE project_type=999 and evaluate_project_id = #{productId}")
    Integer countEvaluateByProductId(@Param("productId") Long productId);

}