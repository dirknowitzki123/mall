package com.kingyon.chengxin.framework;


import com.kingyon.chengxin.framework.exception.SysException;

import java.io.Serializable;

public class AbstractEntity implements /*JsonClassSerializer,*/ Serializable {
    protected static long serialVersionUID = 1L;

    /**
     * 对象值拷贝
     * @param to    目标对象类
     * @param <T>
     * @return      目标对象
     * @throws Exception
     */
    public <T> T copyTo(Class<T> to)  {
        T obj = null;

        try {
            obj = (T) ObjectCopy.copyTo(this, to);
        }catch(Exception e) {
            throw new SysException(SysErrorCode.OBJECT_COPY_ERROR, e.getMessage());
        }
        return obj;
    }

}
