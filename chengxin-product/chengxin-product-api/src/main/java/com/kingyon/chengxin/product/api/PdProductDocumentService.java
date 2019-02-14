package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.product.dto.ProductDocumentDto;

import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 09:52
 */
public interface PdProductDocumentService extends BaseService {
    /**
     * 拉取第三方文档
     * @param productCode
     * @param id
     * @return
     */
    List<ProductDocumentDto> pullProductDoc(String productCode, Long id) throws Exception;

    /**
     *  添加或编辑文档
     * @param documentDto
     * @return
     */
    Boolean addOrEdit(ProductDocumentDto documentDto) throws Exception;

    /**
     * 逻辑删除
     * @param id
     * @return
     */

    Boolean delete(Long id);

    List<ProductDocumentDto>  productDocListByProductId(Long productId) throws Exception;

    List<ProductDocumentDto> openBuyTold(Long productId, Long docId) throws Exception;
}
