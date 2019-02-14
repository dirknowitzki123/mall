package com.kingyon.chengxin.framework.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 自定义的JSON转换MAPPER
 */

@Configuration
@Order(-20)
@Slf4j
public class CustomJacksonSerializer {

    @Bean(name = "jacksonMapper")
    public CustomObjectMapper initCustomObjectMapper() {
        return new CustomObjectMapper();
    }

    public class CustomObjectMapper extends ObjectMapper {

        public CustomObjectMapper() {
            super();
            // 设置null转换""
//            getSerializerProvider().setNullValueSerializer(new NullSerializer());
//            this.setSerializationInclusion(JsonInclude.Include.NON_NULL);

//             设置日期转换yyyy-MM-dd HH:mm:ss
            setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }

    }

    // null的JSON序列
    private class NullSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen,
                              SerializerProvider provider) throws IOException,
                JsonProcessingException {
//            log.info("value = {}", value);
//            if (null == value) {
//                jgen.writeStartArray();
//                jgen.writeEndArray();
//            } else {
//                jgen.writeObject(value);
//            }
//            jgen.writeString("");
        }
    }

}
