package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.product.dto.ProductInfoDetail;
import com.kingyon.chengxin.product.dto.ProductInfoDto;
import com.kingyon.chengxin.product.dto.query.ProductInfoQuery;
import com.kingyon.chengxin.product.dto.response.ChannelProductDto;
import com.kingyon.chengxin.product.dto.response.SupplierDictionariesDto;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/15 0015 11:43
 */
public interface PdProductInfoService extends BaseService {
    /**
     * 产品列表
     *
     * @param infoQuery
     * @return
     * @throws Exception
     */
    public PageDto productList(ProductInfoQuery infoQuery) throws Exception;

    /**
     * 添加或修改产品
     *
     * @param productInfoDto
     * @return
     * @throws Exception
     */
    ProductInfoDto addOrEdit(ProductInfoDto productInfoDto) throws Exception;

    /**
     * 获取供应商产品列表
     *
     * @return
     * @throws Exception
     */
    List<ChannelProductDto> channelProductList(Integer channelId) throws Exception;

    /**
     * 获取供应商产品详情
     *
     * @param productCode
     * @return
     * @throws Exception
     */
    ProductInfoDto copySupplierProductInfo(String productCode, Integer channelId) throws Exception;

    /**
     * 获取产品详情
     *
     * @param productId
     * @return
     */

    ProductInfoDetail getProductInfoDetail(Long productId,byte status) throws Exception;

    /**
     * 服务商列表
     *
     * @return
     */
    List<SupplierDictionariesDto> SupplierList();

    /**
     * 用户端商品列表
     *
     * @param query
     * @return
     */
    PageDto selectProductList(ProductInfoQuery query);

    /**
     * 默认试算
     * @param productId
     * @return
     * @throws Exception
     */
    Object defaultTrial(Long productId) throws Exception;

    /**
     * 投保属性
     * @param productId
     * @return
     */
    Map<String, Object> productInsuredAttr(Long productId) throws Exception;

    /**
     * 修改商品SKU属性状态
     * @param productId
     * @param skuId
     * @return
     */
    Boolean changeSkuStatus(Long productId, Long skuId);
}
