package com.kingyon.chengxin.framework.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 可使用Json的多态序列化对象(v1.0, 兼容旧版)
 * Created by Administrator on 16-11-25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class CacheSerializable<T> {
        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property="@class" )
        T object;
}
