package com.kingyon.chengxin.product.service;

import com.kingyon.chengxin.framework.ObjectCopy;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.framework.net.OkHttpRest;
import com.kingyon.chengxin.insurance.api.QxApiService;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.PdProductDocumentService;
import com.kingyon.chengxin.product.dto.ProductDocumentDto;
import com.kingyon.chengxin.product.enums.DocumentType;
import com.kingyon.chengxin.product.mapper.PdProductDocumentMapper;
import com.kingyon.chengxin.product.mapper.PdProductInfoMapper;
import com.kingyon.chengxin.product.modal.PdProductDocument;
import com.kingyon.chengxin.product.modal.PdProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/17 0017 09:53
 */
@DubboService
public class PdProductDocumentServiceImpl implements PdProductDocumentService {

    @Autowired
    PdProductInfoMapper productInfoMapper;

    @Autowired
    PdProductDocumentMapper productDocumentMapper;

    @DubboReference
    QxApiService qxApiService;


    @Override
    public List<ProductDocumentDto> pullProductDoc(String productCode, Long productId) throws Exception {
        PdProductInfo pdProductInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (pdProductInfo == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_PRODUCT);
        }
        productDocumentMapper.logicDeleteByProductId(productId);
        List<ProductDocumentDto> document = getQXDocument(productCode, productId);
        List<PdProductDocument> documentList = ObjectCopy.copyTo(document, PdProductDocument.class);
        int i = productDocumentMapper.insertBatch(documentList);
        if (i > 0) {
            List<PdProductDocument> documents = productDocumentMapper.productDocListByProductId(productId);
            return ObjectCopy.copyTo(documents, ProductDocumentDto.class);
        }
        throw new ServiceException(ProductErrorCode.SAVE_FAIL);
    }

    @Override
    public Boolean addOrEdit(ProductDocumentDto documentDto) throws Exception {
        PdProductDocument doc = ObjectCopy.copyTo(documentDto, PdProductDocument.class);
        int flag = 0;
        if (documentDto.getId() == null) {
            flag = productDocumentMapper.insert(doc);
        } else {
            flag = productDocumentMapper.updateByPrimaryKeySelective(doc);
        }
        return flag > 0;
    }

    @Override
    public Boolean delete(Long id) {
        int i = productDocumentMapper.deleteByPrimaryKey(id);
        return i > 0;
    }

    @Override
    public List<ProductDocumentDto> productDocListByProductId(Long productId) throws Exception {
        List<PdProductDocument> documents = productDocumentMapper.productDocListByProductId(productId);
        return ObjectCopy.copyTo(documents, ProductDocumentDto.class);
    }

    @Override
    @Transactional
    public List<ProductDocumentDto> openBuyTold(Long productId, Long docId) throws Exception {
        PdProductDocument one = productDocumentMapper.checkBuyToldByProductId(productId, 1);
        if (one != null) {
            one.setBuyTold((byte) 0);
            productDocumentMapper.updateByPrimaryKeySelective(one);
            if (one.getId().equals(docId)) {
                return productDocListByProductId(productId);
            }
        }
        PdProductDocument productDocument = new PdProductDocument();
        productDocument.setId(docId);
        productDocument.setBuyTold((byte) 1);
        int i = productDocumentMapper.updateByPrimaryKeySelective(productDocument);
        return productDocListByProductId(productId);
    }

    private List<ProductDocumentDto> getQXDocument(String productCode, Long productId) throws Exception {
        Map detail = (Map) qxApiService.productDetail(productCode);
        List<ProductDocumentDto> documentDtoList = new ArrayList<>();
        List<Map<String, String>> masterProvisions = (List) detail.get("masterProvisions");
        Date date = new Date();
        loopDoc(productId, documentDtoList, masterProvisions, date);
        List<Map<String, String>> additionalProvisions = (List) detail.get("additionalProvisions");
        loopDoc(productId, documentDtoList, additionalProvisions, date);

        String premiumTable = (String) detail.get("premiumTable");
        if (premiumTable != null) {
            ProductDocumentDto docPremiumTable = new ProductDocumentDto();
            docPremiumTable.setDocName("费率表");
            docPremiumTable.setDocDigest("费率表.pdf");
            docPremiumTable.setDocType(DocumentType.UPLOAD_DOC.getCode() + "");
            docPremiumTable.setDocUrl(premiumTable);
            docPremiumTable.setProductId(productId);
            docPremiumTable.setModifyTime(date);
            docPremiumTable.setOperator(9999l);
            documentDtoList.add(docPremiumTable);

        }

        String samplePolicy = (String) detail.get("samplePolicy");
        if (samplePolicy != null) {
            ProductDocumentDto docPremiumTable = new ProductDocumentDto();
            docPremiumTable.setDocName("保单样本");
            docPremiumTable.setDocDigest("保单样本.pdf");
            docPremiumTable.setDocType(DocumentType.UPLOAD_DOC.getCode() + "");
            docPremiumTable.setDocUrl(samplePolicy);
            docPremiumTable.setProductId(productId);
            docPremiumTable.setModifyTime(date);
            docPremiumTable.setOperator(9999l);
            documentDtoList.add(docPremiumTable);
        }
        String pictureNotify = (String) detail.get("pictureNotify");
        if (samplePolicy != null) {
            ProductDocumentDto docPremiumTable = new ProductDocumentDto();
            docPremiumTable.setDocName("客户告知书");
            docPremiumTable.setDocDigest("客户告知书.pdf");
            docPremiumTable.setDocType(DocumentType.UPLOAD_DOC.getCode() + "");
            docPremiumTable.setDocUrl(pictureNotify);
            docPremiumTable.setProductId(productId);
            docPremiumTable.setModifyTime(date);
            docPremiumTable.setOperator(9999l);
            documentDtoList.add(docPremiumTable);
        }

        return documentDtoList;
    }


    private void loopDoc(Long productId, List<ProductDocumentDto> documentDtoList, List<Map<String, String>> masterProvisions, Date date) {
        if (null != masterProvisions && masterProvisions.size() > 0) {
            for (Map<String, String> masterProvision : masterProvisions) {
                ProductDocumentDto document = new ProductDocumentDto();
                String title = masterProvision.get("title");
                String attachmentUrl = masterProvision.get("attachmentUrl");
                String content = masterProvision.get("content");
                String digest = content == null ? attachmentUrl : content;
                Integer docType = content == null ? DocumentType.UPLOAD_DOC.getCode() : DocumentType.RICH_TEXT.getCode();
                document.setDocName(title);
                document.setDocDigest(digest);
                document.setDocType(docType + "");
                document.setDocUrl(attachmentUrl);
                document.setProductId(productId);
                document.setModifyTime(date);
                document.setOperator(9999l);
                documentDtoList.add(document);
            }
        }
    }


}
