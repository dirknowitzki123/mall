package com.kingyon.chengxin.product.web.wxutil;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * xml工具类
 *
 * @author miklchen
 */
public class XMLUtil {


    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    public static org.w3c.dom.Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }

    /**
     * Map转换成Xml
     * @param map
     * @return
     */
    public static String map2Xmlstring(Map<String,String> map){
        StringBuffer sb = new StringBuffer("");
        sb.append("<xml>");

        Set<String> set = map.keySet();
        for(Iterator<String> it=set.iterator(); it.hasNext();){
            String key = it.next();
            Object value = map.get(key);
            sb.append("<").append(key).append(">");
            sb.append(value);
            sb.append("</").append(key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * Xml string转换成Map
     * @param xmlStr
     * @return
     */
    public static Map<String,String> xmlString2Map(String xmlStr){
        Map<String,String> map = new HashMap<String,String>();
        Document doc;
        try {
            doc =  DocumentHelper.parseText(xmlStr);
            Element el = doc.getRootElement();
            map = recGetXmlElementValue(el,map);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 循环解析xml
     * @param ele
     * @param map
     * @return
     */
    private static Map<String, String> recGetXmlElementValue(Element ele, Map<String, String> map){
        List<Element> eleList = ele.elements();
        if (eleList.size() == 0) {
            map.put(ele.getName(), ele.getTextTrim());
            return map;
        } else {
            for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
                Element innerEle = iter.next();
                recGetXmlElementValue(innerEle, map);
            }
            return map;
        }
    }

    public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value !=null)map.put(fieldName, value.toString());

        }
        return map;
    }

}
