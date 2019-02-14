package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.product.dto.OrderEvaluateDto;

import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 10:42
 */
public interface OmOrderEvaluateService extends BaseService {
    /**
     * 添加后台评论
     * @param evaluateDto
     * @return
     */
    Boolean addOrEdit(OrderEvaluateDto evaluateDto);

    /**
     * 删除后台评论
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     *  后台评论列表
     * @param productId
     * @return
     */
    List<OrderEvaluateDto> evaluateBossList(Long productId);

    /**
     * 根据产品ID统计评论数
     * @return
     */
   Integer countEvaluateByProductId(Long productId);

}
