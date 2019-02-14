package com.kingyon.chengxin.insurance.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.insurance.modal.QxOrderLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.ImportResource;


@Mapper
@ImportResource("/mappers/insurance/QxOrderLogMapper.xml")
public interface QxOrderLogMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QxOrderLog record);

    int insertSelective(QxOrderLog record);

    QxOrderLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QxOrderLog record);

    int updateByPrimaryKey(QxOrderLog record);
}