package com.kingyon.chengxin.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingyon.chengxin.framework.ObjectCopy;
import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.insurance.api.QxApiService;
import com.kingyon.chengxin.insurance.dto.qixindto.QxProduct;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderEvaluateService;
import com.kingyon.chengxin.product.api.PdProductDocumentService;
import com.kingyon.chengxin.product.api.PdProductInfoService;
import com.kingyon.chengxin.product.dto.*;
import com.kingyon.chengxin.product.dto.query.ProductInfoQuery;
import com.kingyon.chengxin.product.dto.response.ProductListDto;
import com.kingyon.chengxin.product.dto.response.ChannelProductDto;
import com.kingyon.chengxin.product.dto.response.SupplierDictionariesDto;
import com.kingyon.chengxin.product.enums.ChannelType;
import com.kingyon.chengxin.product.enums.ProductType;
import com.kingyon.chengxin.product.mapper.CmSupplierDictionariesMapper;
import com.kingyon.chengxin.product.mapper.PdChannelProductMapper;
import com.kingyon.chengxin.product.mapper.PdProductInfoMapper;
import com.kingyon.chengxin.product.mapper.PdSkuMapper;
import com.kingyon.chengxin.product.modal.CmSupplierDictionaries;
import com.kingyon.chengxin.product.modal.PdChannelProduct;
import com.kingyon.chengxin.product.modal.PdProductInfo;
import com.kingyon.chengxin.product.modal.PdSku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/15 0015 11:55
 */
@DubboService
@Slf4j
public class PdProductInfoServiceImpl implements PdProductInfoService, BaseService {


    @Autowired
    PdProductInfoMapper productInfoMapper;

    @Autowired
    CmSupplierDictionariesMapper cmSupplierDictionariesMapper;

    @Autowired
    PdChannelProductMapper channelProductMapper;

    @DubboReference
    QxApiService qxApiService;

    @Autowired
    OmOrderEvaluateService orderEvaluateService;

    @Autowired
    PdProductDocumentService productDocumentService;


    @Autowired
    PdSkuMapper pdSkuMapper;

    @Override
    public PageDto productList(ProductInfoQuery infoQuery) throws Exception {
        PageHelper.startPage(infoQuery.getPageIndex(), infoQuery.getPageSize());
        List<ProductListDto> infos = productInfoMapper.selectProductListBoss(infoQuery);
        PageInfo pageInfo = new PageInfo(infos);
        PageDto pageDto = new PageDto();
        pageDto.setDataCount(Integer.parseInt(pageInfo.getTotal() + ""));
        pageDto.setPageIndex(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setData(infos);
        return pageDto;
    }

    @Override
    @Transactional
    public ProductInfoDto addOrEdit(ProductInfoDto productInfoDto) throws Exception {
        PdProductInfo info = null;
        try {
            info = ObjectCopy.copyTo(productInfoDto, PdProductInfo.class);
            info.setProductCode(info.getProductName().trim());
            info.setDeleted((byte) 0);
            if (null == productInfoDto.getId()) {
                //保存商品的SKU属性
                productInfoMapper.insert(info);
                if (info.getProType().equals(ProductType.ENTITY.getCode())) {
                    saveSku(info.getId(), productInfoDto.getSkus());
                }
            } else {
                productInfoMapper.updateByPrimaryKeySelective(info);
                if (info.getProType().equals(ProductType.ENTITY.getCode())) {
                    saveSku(info.getId(), productInfoDto.getSkus());
                }
            }
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                throw e;
            }

        }
        return productInfoDto;
    }

    @Override
    public List<ChannelProductDto> channelProductList(Integer channelId) throws Exception {
        List<ChannelProductDto> channelProductList = new ArrayList<>();
        if (channelId.equals(ChannelType.TK_TAIKANG.getCode())) {
            //泰康产品列表从数据库取
            List<PdChannelProduct> channelProducts = channelProductMapper.getAllByChannelId(channelId);
            channelProductList = ObjectCopy.copyTo(channelProducts, ChannelProductDto.class);
        } else {
            //齐欣产品列表,通过齐欣API获取
            List<QxProduct> qxProducts = qxApiService.productList();
            channelProductList = ObjectCopy.copyTo(qxProducts, ChannelProductDto.class);
        }

        List<String> productCodeList = productInfoMapper.productCodeList();
        for (ChannelProductDto qxProductRespons : channelProductList) {
            boolean contains = productCodeList.contains(qxProductRespons.getProductCode());
            if (contains) {
                qxProductRespons.setFlag(true);
            }
        }
        Collections.sort(channelProductList);
        return channelProductList;
    }

    @Override
    public ProductInfoDto copySupplierProductInfo(String productCode, Integer channelId) throws Exception {

        ProductInfoDto productInfoDto = new ProductInfoDto();
        if (channelId.equals(ChannelType.TK_TAIKANG.getCode())) {
            PdChannelProduct pdChannelProduct = channelProductMapper.getOne(productCode, channelId);
            productInfoDto.setProductName(pdChannelProduct.getProdName());
            productInfoDto.setSupplierId(saveSupplier(pdChannelProduct.getCompanyName()));
            productInfoDto.setProductCode(productCode);
            productInfoDto.setChannelId(channelId.longValue());
            productInfoDto.setProType(ProductType.INSURANCE.getCode());
            return productInfoDto;
        }

        Map detail = (Map) qxApiService.productDetail(productCode);
        String productName = (String) detail.get("productName");
        String planName = (String) detail.get("planName");
        String proName = planName == null ? productName : productName + "-" + planName;
        String productDetail = (String) detail.get("productRead");
        Integer priceLong = (Integer) detail.get("price");
        String companyCnName = (String) detail.get("companyCnName");


        BigDecimal bigDecima = new BigDecimal(priceLong);
        BigDecimal price = bigDecima.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        List<Map<String, String>> features = (List) detail.get("features");
        StringBuffer tag = new StringBuffer();
        for (Map<String, String> feature : features) {
            tag.append(feature.get("content")).append(",");
        }
        productInfoDto.setProductName(proName);
        productInfoDto.setProductDetail(productDetail);
        productInfoDto.setPrice(price);
        productInfoDto.setTag(tag.toString());
        productInfoDto.setProType(ProductType.INSURANCE.getCode());
        productInfoDto.setProductCode(productCode);
        productInfoDto.setSupplierId(saveSupplier(companyCnName));
        productInfoDto.setChannelId(ChannelType.QX_HUIZHE.getCode().longValue());
        return productInfoDto;
    }

    @Override
    public ProductInfoDetail getProductInfoDetail(Long productId, byte status) throws Exception {
        PdProductInfo pdProductInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (pdProductInfo == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_PRODUCT);
        }

        ProductInfoDetail productInfoDetail = ObjectCopy.copyTo(pdProductInfo, ProductInfoDetail.class);
        List<OrderEvaluateDto> evaluateBossList = orderEvaluateService.evaluateBossList(productId);
        List<ProductDocumentDto> documentDtoList = productDocumentService.productDocListByProductId(productId);
        List<PdSku> skus = pdSkuMapper.selectByProductId(productId, status);
        Integer evaluateSum = orderEvaluateService.countEvaluateByProductId(productId);
        productInfoDetail.setEvaluateSum(evaluateSum);
        productInfoDetail.setOrderEvaluates(evaluateBossList);
        productInfoDetail.setProductDocuments(documentDtoList);
        productInfoDetail.setSkus(ObjectCopy.copyTo(skus, SkuDto.class));
        return productInfoDetail;

    }

    @Override
    public List<SupplierDictionariesDto> SupplierList() {
        List<CmSupplierDictionaries> cmSupplierDictionaries = cmSupplierDictionariesMapper.getAll();
        return ObjectCopy.copyTo(cmSupplierDictionaries, SupplierDictionariesDto.class);
    }

    @Override
    public PageDto selectProductList(ProductInfoQuery query) {
        query.setPutaway(new Byte("1"));
        PageDto pageDto = new PageDto();
        PageHelper.startPage(query.getPageIndex(), query.getPageSize());
        List<PdProductInfo> productInfoList = productInfoMapper.selectProductList(query);
        List<ProductInfoDto> productInfoDtos = ObjectCopy.copyTo(productInfoList, ProductInfoDto.class);
        PageInfo pageInfo = new PageInfo(productInfoList);
        pageDto.setDataCount(Integer.parseInt(pageInfo.getTotal() + ""));
        pageDto.setPageIndex(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setData(productInfoDtos);
        return pageDto;
    }

    @Override
    public Object defaultTrial(Long productId) throws Exception {
        PdProductInfo pdProductInfo = productInfoMapper.selectByPrimaryKey(productId);
        String productCode = pdProductInfo.getProductCode();
        if (null == productCode) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_PRODUCT, "未找到对应的产品编码");
        }
        Map<String, Object> defaultTrial = (Map) qxApiService.defaultTrial(productCode);
        defaultTrial.remove("partnerId");
        return defaultTrial;
    }

    @Override
    public Map<String, Object> productInsuredAttr(Long productId) throws Exception {
        PdProductInfo pdProductInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (null == pdProductInfo && null == pdProductInfo.getProductCode()) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_PRODUCT, "未找到对应的产品编码");
        }
        String productCode = pdProductInfo.getProductCode();
        Map<String, Object> insuredAttr = (Map) qxApiService.productInsuredAttr(productCode);
        Map<String, Object> insureAttribute = (Map) insuredAttr.get("insureAttribute");
        Integer healthWebId = (Integer) insureAttribute.get("healthWebId");
        insuredAttr.put("healthStatement", false);
        if (null != healthWebId && healthWebId > 0) {
            insuredAttr.put("healthStatement", true);
        }
        insuredAttr.put("productId", productId);

        return insuredAttr;
    }

    @Override
    public Boolean changeSkuStatus(Long productId, Long skuId) {
        int i = pdSkuMapper.changeSkuStatus(productId, skuId);
        return i > 0;
    }


    private Long saveSupplier(String name) {
        CmSupplierDictionaries one = cmSupplierDictionariesMapper.getOneByName(name);
        if (one == null) {
            CmSupplierDictionaries dictionaries = new CmSupplierDictionaries();
            dictionaries.setSupplierName(name);
            cmSupplierDictionariesMapper.insert(dictionaries);
            CmSupplierDictionaries oneByName = cmSupplierDictionariesMapper.getOneByName(name);
            return oneByName.getId();
        }
        return one.getId();
    }


    public void saveSku(Long productId, List<SkuDto> skus) throws Exception {

        if (null == skus || skus.size() == 0) {
            throw new ServiceException(ProductErrorCode.SAVE_FAIL, "请添加商品SKU属性");
        }
        pdSkuMapper.logicDelByProductId(productId);
        for (SkuDto skuDto : skus) {
            PdSku pdSku = ObjectCopy.copyTo(skuDto, PdSku.class);
            pdSku.setProductId(productId);
            pdSku.setStatus((byte) 1);
            pdSku.setStock(0);
            pdSkuMapper.insert(pdSku);
        }
    }


}
