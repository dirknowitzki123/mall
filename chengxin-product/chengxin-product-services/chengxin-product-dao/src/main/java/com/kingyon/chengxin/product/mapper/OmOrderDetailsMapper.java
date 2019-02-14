package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.modal.OmOrderDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmOrderDetailsMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OmOrderDetails record);

    int insertSelective(OmOrderDetails record);

    OmOrderDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmOrderDetails record);

    int updateByPrimaryKey(OmOrderDetails record);

    OmOrderDetails selectByOrderNumber(@Param("orderNumber") String orderNumber);

    OmOrderDetails selectDetailsOrderId(@Param("detailsOrderId") String detailsOrderId);

}