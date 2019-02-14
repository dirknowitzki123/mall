package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.api.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2019/1/4 0004.
 */
public interface SysCodeInfoService extends BaseService {
    /**
     * 添加品牌码值
     * @param brandName
     * @param id
     * @return
     */
   Boolean addBrand(String brandName, Long id);

    /**
     * 查询所有品牌码值
     * @return
     */
   List<Map<String,Object>> selectBrandCodeList();

}
