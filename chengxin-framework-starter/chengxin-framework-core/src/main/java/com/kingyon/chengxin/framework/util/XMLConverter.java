package com.kingyon.chengxin.framework.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 16-9-19.
 */
@Slf4j
public class XMLConverter {
    private static final XmlMapper xmlMapper = new XmlMapper();

    static {
        xmlMapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
            private static final long serialVersionUID = 1L;
            // 反序列化时调用
            @Override
            public String nameForSetterMethod(MapperConfig<?> config,
                                              AnnotatedMethod method, String defaultName) {
                return method.getName().substring(3);
            }
            // 序列化时调用
            @Override
            public String nameForGetterMethod(MapperConfig<?> config,
                                              AnnotatedMethod method, String defaultName) {
                return method.getName().substring(3);
            }
        });
        xmlMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
        xmlMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true) ;
        xmlMapper.configure(DeserializationFeature.WRAP_EXCEPTIONS, false) ;
    }

    /**
     * XML To Object
     *
     * @param xmlData
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(String xmlData, Class<T> cls) {
        try {
            return xmlMapper.readValue(xmlData, cls);
        }catch (Exception e) {
            throw new IllegalArgumentException("xmlToBean:" , e);
        }
    }

    /**
     * @param xmlFile
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(File xmlFile, Class<T> cls) throws IOException {

        T obj = xmlMapper.readValue(xmlFile, cls);
        return obj;
    }

    /**
     * @param xmlInputStream
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(InputStream xmlInputStream, Class<T> cls) throws IOException {
        T obj = xmlMapper.readValue(xmlInputStream, cls);
        return obj;
    }

    public static <T> String beanToXml(T bean)  {
        try
        {
            return xmlMapper.writeValueAsString(bean);
        }catch (Exception e) {
            throw new IllegalArgumentException("beanToXml:" , e);
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JacksonXmlRootElement(localName="ROOT")
    static class TestDto {
        String TOKEN;
        String SERVICE;
        String DATAPARAM;
    }

    public static void main(String[] args) {
//        xmlMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));

        String xml;
        TestDto testDto = new TestDto("token111", "service------------", "data-22222");
        xml = beanToXml(testDto);
        System.out.println("-----:" + xml);

        xml = "<GROUP><RQ>2017-10-18</RQ><HBLIST><HB><HM>830</HM><YSID>21</YSID><YS>刘艳</YS><KSID>82</KSID><KSMC>门诊儿科</KSMC><ZC>副主任医师</ZC>" +
                "<XMID>22962</XMID><HYMC>副主任医师挂号费</HYMC><YGHS>0</YGHS><SYHS>100</SYHS><DJ>11</DJ><HCXH /><HL>门诊</HL><FSD>1</FSD>" +
                "<KGSJ>07:00-06:59</KGSJ><FWMC>全日</FWMC>" +
                "<SPANLIST>" +
                "<SPAN><SJD>08:00:00-08:05:00</SJD><SL>1</SL></SPAN>" +
                "<SPAN><SJD>08:05:00-08:10:00</SJD><SL>1</SL></SPAN>" +
                "<SPAN><SJD>08:10:00-08:15:00</SJD><SL>1</SL></SPAN>" +
                "</SPANLIST>" +
                "</HB></HBLIST></GROUP>";

//        xml = "<ROOT><TOKEN><![CDATA[testttttss"
//                + "]]></TOKEN><SERVICE>10:15:00-10:20:00</SERVICE><DATAPARAM><![CDATA[" +
//                "222222222223]]></DATAPARAM></ROOT>";
        SignalSourceFirst first = xmlToBean(xml, SignalSourceFirst.class );

        xml = beanToXml(first);
        System.out.println("-----:" + xml);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JacksonXmlRootElement(localName="GROUP")
    public static class SignalSourceFirst {
        String RQ;
        SignalSourceSecond HBLIST;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
//    @JacksonXmlRootElement(localName="GROUP")
    public static class SignalSourceSecond {
        SignalSourceThird HB;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
//    @JacksonXmlRootElement(localName="GROUP")
    public static class SignalSourceThird {
        Long XMID;
        String HYMC;
        Long HM;
        Long YSID;
        String YS;
        String ZC;
        Long KSID;
        String KSMC;
        int YGHS;
        int SYHS;
        Long DJ;
        String HL;
        short HCXH;
        short FSD;
        String KGSJ;
        String FWMC;
//        SignalSourceFourth SPANLIST;
        ArrayList<SignalSourceFifth> SPANLIST;
    }

//    @Data
////    @AllArgsConstructor
//    @NoArgsConstructor
//    @JacksonXmlRootElement(localName="SPANLIST")
//    public static class SignalSourceFourth {
//
//        SignalSourceFifth SPAN;
//
//    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JacksonXmlRootElement(localName="SPAN")
    public static class SignalSourceFifth {
        String SJD;
        String SL;
    }
}
