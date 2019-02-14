package com.kingyon.chengxin.framework.util;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 可使用Json的多态序列化对象
 * Created by Administrator on 16-11-25.
 */

public class EnableSerializable {
    /**
     * 这里使用短类名，以降低附加序列化长度
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@t")
    @JsonSubTypes({@JsonSubTypes.Type(value=O.class,name = "O"),    //Object
            @JsonSubTypes.Type(value=L.class,name = "L"),   //List
            @JsonSubTypes.Type(value=Q.class,name = "Q"),   //Queue
            @JsonSubTypes.Type(value=S.class,name = "S"),   //Set
            @JsonSubTypes.Type(value=C.class,name = "C"),   //Collection
            @JsonSubTypes.Type(value=M.class,name = "M")})  //Map
    public static class O{
        @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
        Object o;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class L extends O{
        @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
        List o;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class S extends O{
        @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
        Set o;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Q extends O{
        @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
        Queue o;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class C extends O{
        @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
        Collection o;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class M extends O{
        @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
        Map o;
    }

    public static O getObjectSerializable(Object object) {
        O obj = null;
        if (object instanceof List ) {
            obj = new L((List)object);
        } else if (object instanceof Queue ) {
            obj = new Q((Queue)object);
        } else if (object instanceof Set ) {
            obj = new S((Set)object);
        } else if (object instanceof Collection ) {
            obj = new C((Collection)object);
        } else if (object instanceof Map ) {
            obj = new M((Map)object);
        } else
            obj = new O(object );
        return obj;
    }
}
