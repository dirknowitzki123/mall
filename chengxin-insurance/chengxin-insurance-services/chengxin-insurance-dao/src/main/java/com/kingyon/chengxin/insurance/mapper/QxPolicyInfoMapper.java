package com.kingyon.chengxin.insurance.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.insurance.modal.QxPolicyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.ImportResource;


@Mapper
@ImportResource("/mappers/insurance/QxPolicyInfoMapper.xml")
public interface QxPolicyInfoMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QxPolicyInfo record);

    int insertSelective(QxPolicyInfo record);

    QxPolicyInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QxPolicyInfo record);

    int updateByPrimaryKey(QxPolicyInfo record);
}