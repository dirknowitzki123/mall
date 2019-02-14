package com.kingyon.chengxin.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Cache对象（反）序列化，装入可实例化的实体CacheSerializable
 * Created by Administrator on 16-11-25.
 */
@Slf4j
public class ObjectSerializer {

    public static String toJson(Object object) {
        EnableSerializable.O obj = EnableSerializable.getObjectSerializable(object);

        String val = Converter.toJson(obj, false, false);
        return val;
    }
    /**
     * 使用Json序列化
     * @param object
     * @return
     */
    public static byte[] toByte(Object object) {
        return Converter.toUTF8(toJson(object));
    }

    /**
     * 反序列化对象：Serializable或json对象
     * @param bytes
     * @return
     */
    public static <T>T toObject(byte[] bytes) {
        if (bytes == null)
            return null;

        if (bytes[0] == '[' || bytes[0] == '{') {
            if (Arrays.binarySearch(bytes, 2, 4, (byte)'@') > 0) {
                EnableSerializable.O obj = Converter.parseObject(bytes, EnableSerializable.O.class);
                return (T) obj.getO();
            } else {
                //兼容旧版本的缓存数据
                CacheSerializable obj = Converter.parseObject(bytes, CacheSerializable.class);
                return (T) obj.getObject();
            }
        } else {
            return (T) Converter.unSerializeObject(bytes);
        }
    }

    public static <T>T toObject(String value) {
        if (value == null)
            return null;

        return (T) toObject(Converter.toUTF8(value)) ;
    }


//    public static <T>T toObject(byte[] bytes, Class<?>... fieldClazz) {
//        if (bytes == null)
//            return null;
//
//        if (bytes[0] == '[' || bytes[0] == '{') {
////            log.debug("toObject: of JsonClassSerializer ");
//            EnableSerializable.ObjectSerializable obj = Converter.parseObject(bytes , EnableSerializable.ObjectSerializable.class, fieldClazz);
//            return (T)obj.getObject();
//        } else {
//            return (T)Converter.unSerializeObject(bytes);
//        }
//    }
}
