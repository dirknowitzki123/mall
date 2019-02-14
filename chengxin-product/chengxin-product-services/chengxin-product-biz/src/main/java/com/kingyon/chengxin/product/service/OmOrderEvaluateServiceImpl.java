package com.kingyon.chengxin.product.service;

import com.kingyon.chengxin.framework.ObjectCopy;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.product.api.OmOrderEvaluateService;
import com.kingyon.chengxin.product.dto.OrderEvaluateDto;
import com.kingyon.chengxin.product.mapper.OmOrderEvaluateMapper;
import com.kingyon.chengxin.product.modal.OmOrderEvaluate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 10:57
 */
@DubboService
@Slf4j
public class OmOrderEvaluateServiceImpl implements OmOrderEvaluateService {

    @Autowired
    OmOrderEvaluateMapper orderEvaluateMapper;

    public static final Integer PRODUC_TYPE = 999;
    public static final Boolean VISIBLE = true;
    public static final Boolean EVALUATE_TYPE = true;

    @Override
    public Boolean addOrEdit(OrderEvaluateDto evaluateDto) {
        OmOrderEvaluate omOrderEvaluate = evaluateDto.copyTo(OmOrderEvaluate.class);
        int flag = 0;
        if (evaluateDto.getId() == null) {
            omOrderEvaluate.setProjectType(PRODUC_TYPE);
            omOrderEvaluate.setVisible(VISIBLE);
            omOrderEvaluate.setEvaluateType(EVALUATE_TYPE);
            flag = orderEvaluateMapper.insert(omOrderEvaluate);
        } else {
            flag = orderEvaluateMapper.updateByPrimaryKeySelective(omOrderEvaluate);
        }
        return flag > 0;
    }

    @Override
    public Boolean delete(Long id) {
        int i = orderEvaluateMapper.deleteByPrimaryKey(id);
        return i > 0;
    }

    @Override
    public List<OrderEvaluateDto> evaluateBossList(Long productId) {
        OmOrderEvaluate orderEvaluate = new OmOrderEvaluate();
        orderEvaluate.setProjectType(PRODUC_TYPE);
        orderEvaluate.setEvaluateType(EVALUATE_TYPE);
        orderEvaluate.setEvaluateProjectId(productId);
        List<OmOrderEvaluate> evaluates = orderEvaluateMapper.evaluateList(orderEvaluate);
        return ObjectCopy.copyTo(evaluates, OrderEvaluateDto.class);
    }

    @Override
    public Integer countEvaluateByProductId(Long productId) {
        return orderEvaluateMapper.countEvaluateByProductId(productId);
    }
}
