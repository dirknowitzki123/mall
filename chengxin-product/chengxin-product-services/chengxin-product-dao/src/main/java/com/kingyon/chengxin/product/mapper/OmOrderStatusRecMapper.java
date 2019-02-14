package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.OmOrderStatusRec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OmOrderStatusRecMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OmOrderStatusRec record);

    int insertSelective(OmOrderStatusRec record);

    OmOrderStatusRec selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmOrderStatusRec record);

    int updateByPrimaryKey(OmOrderStatusRec record);

    @Select("select CASE status WHEN 10 then '待付款' when 11 then '已取消' when 12 then '待发货' when 13 then '退款中' when 14 then '已退款' " +
            "when 15 then '待收货' when 16 then '退货中' when 17 then '退货失败' when 18 then '已收货' when 19 then '退货完成' end as status , " +
            "status as statusCode, create_time as create_time from om_order_status_rec where order_number = #{orderNumber} order by create_time desc")
    List<Map<String, Object>> selectList(@Param("orderNumber") String orderNumber);
}