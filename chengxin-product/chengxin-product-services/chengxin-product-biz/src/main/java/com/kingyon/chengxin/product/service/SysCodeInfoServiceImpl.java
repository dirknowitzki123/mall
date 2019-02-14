package com.kingyon.chengxin.product.service;

import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.SysCodeInfoService;
import com.kingyon.chengxin.product.mapper.SysCodeInfoMapper;
import com.kingyon.chengxin.product.modal.SysCodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2019/1/4 0004.
 */
@DubboService
public class SysCodeInfoServiceImpl implements SysCodeInfoService {

    @Autowired
    SysCodeInfoMapper codeInfoMapper;

    @Override
    @Transactional
    public Boolean addBrand(String brandName, Long id) {

        int count = codeInfoMapper.selectByName(brandName);
        if (count > 0 && id == null) {
            throw new ServiceException(ProductErrorCode.PRODUCT_TYPE_NOT_MATCH, "品牌名已存在请不要重复添加");
        }
        int i;
        SysCodeInfo sysCodeInfo = new SysCodeInfo();
        if (id != null) {
            sysCodeInfo.setId(id);
            sysCodeInfo.setCodeName(brandName);
            i = codeInfoMapper.updateByPrimaryKeySelective(sysCodeInfo);
        } else {
            sysCodeInfo.setCodeName(brandName);
            sysCodeInfo.setCodeType("brand_code");
            i = codeInfoMapper.insert(sysCodeInfo);
        }

        return i > 0;
    }

    @Override
    public List<Map<String, Object>> selectBrandCodeList() {
        return codeInfoMapper.selectBrandCodeList();
    }
}
