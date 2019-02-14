package com.kingyon.chengxin.framework;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 含@class的json序列化接口，所有含有多态类的序列化、反序列化均需实现此接口
 * Created by Administrator on 16-11-24.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property="@class")
public interface JsonClassSerializer {
}
