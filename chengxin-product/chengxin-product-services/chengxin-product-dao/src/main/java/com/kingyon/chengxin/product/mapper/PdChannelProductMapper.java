package com.kingyon.chengxin.product.mapper;

import com.kingyon.chengxin.framework.mapper.BaseMapper;
import com.kingyon.chengxin.product.modal.PdChannelProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PdChannelProductMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PdChannelProduct record);

    int insertSelective(PdChannelProduct record);

    PdChannelProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PdChannelProduct record);

    int updateByPrimaryKey(PdChannelProduct record);

    List<PdChannelProduct> getAllByChannelId(@Param("channelId") Integer channelId);

    PdChannelProduct getOne(@Param("productCode") String productCode,@Param("channelId") Integer channelId);
}