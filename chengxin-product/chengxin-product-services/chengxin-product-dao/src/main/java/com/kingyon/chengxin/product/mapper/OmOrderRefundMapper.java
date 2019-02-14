package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.OmOrderRefund;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OmOrderRefundMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OmOrderRefund record);

    int insertSelective(OmOrderRefund record);

    OmOrderRefund selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmOrderRefund record);

    int updateByPrimaryKey(OmOrderRefund record);

    @Select("select id, order_detail_id, refund_money,refund_time,refund_status from om_order_refund where order_detail_id = #{orderDetailId}")
    OmOrderRefund selectByOrderDetailId(@Param("orderDetailId") int orderDetailId);
}