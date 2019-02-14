package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.dto.OrderDto;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.modal.OmOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.omg.CORBA.ObjectHelper;

import java.util.List;
import java.util.Map;

public interface OmOrderMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OmOrder record);

    int insertSelective(OmOrder record);

    OmOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmOrder record);

    int updateByPrimaryKey(OmOrder record);

    Map<String,Object> queryOrderByOrderNum(@Param("accid") Long accid, @Param("orderNum") String orderNum, @Param("payStatus") Integer payStatus);

    List<Map<String,Object>> selectOrderList(OrderQuery orderQuery);


    Map<String,Object> orderDetail(@Param("orderNumber") String orderNumber);

    @Update("update om_order set pay_status=2 where pay_status=0 and status =0 and HOUR( timediff( now(), order_time) )>24")
    void autoCancelOrder();

    @Update("update om_order set pay_status=2, status=11 where pay_status=0 and status =10 and HOUR( timediff( now(), order_time) )>24")
    void autoCancelMallOrder();

    @Select("select o.id as id from om_order o where o.buy_account =#{accid} and o.order_number=#{orderNum}")
    OmOrder selectOrderByOrderNoAndAccid(@Param("accid") Long accid,@Param("orderNum") String orderNum);

    @Select("select o.id as id,o.pay_status as payStatus,amount as amount from om_order o  where o.order_number=#{orderNum}")
    OmOrder selectOrderByOrderNo(@Param("orderNum") String orderNum);

    @Select("SELECT o.order_number as orderNumber,d.product_name as productName,d.project_type as projectType,o.amount as amount,d.buy_num as buy_num,o.pay_status as payStatus," +
            "o.order_time as orderTime,o.status as status,d.spec_model as spec_model, d.main_image as main_image, d.express_company as express_company, d.express_no as express_no " +
            "FROM om_order o LEFT JOIN om_order_details d ON d.order_id = o.id LEFT JOIN pd_product_info p ON d.combo_project_id = p.id WHERE o.order_number = #{orderNumber}")
    Map<String, Object> selectOrderDetail(@Param("orderNumber") String orderNumber);
}