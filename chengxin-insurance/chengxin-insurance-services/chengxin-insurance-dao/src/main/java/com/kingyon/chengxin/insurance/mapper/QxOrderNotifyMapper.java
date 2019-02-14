package com.kingyon.chengxin.insurance.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.insurance.modal.QxOrderNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@Mapper
@ImportResource("/mappers/insurance/QxOrderNotifyMapper.xml")
public interface QxOrderNotifyMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QxOrderNotify record);

    int insertSelective(QxOrderNotify record);

    QxOrderNotify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QxOrderNotify record);

    int updateByPrimaryKey(QxOrderNotify record);

    @Select("select * from qx_order_notify")
    List<QxOrderNotify> getAll();

    @Select("select * from qx_order_notify where insure_num =#{insureNum}")
    QxOrderNotify selectByInsureNum(@Param("insureNum") String insureNum);
}