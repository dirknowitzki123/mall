package com.kingyon.chengxin.framework;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leo on 16/6/6.
 */
@Data
public class PageDto extends AbstractEntity {

    /**
     * 当前第几页
     */
    private int pageIndex = 0;
    /**
     * 每页数据条数
     */
    private int pageSize = 10;

    /**
     * 数据总数
     */
    private int dataCount = 0;

    private List dataList = new ArrayList<>(0);

    private Map<String, Object> otherField = null;

    public PageDto() {
    }

    public PageDto(int pageIndex, int pageSize, List data) {
        this(pageIndex, pageSize, -1, data);
    }

    public PageDto(int pageIndex, int pageSize, int dataCount, List data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.dataCount = dataCount;
        this.setData(data);
    }

    public void setData(List data) {
        this.dataList = data == null ? new ArrayList<>(0) : data;
    }

    public void addField(String key, Object obj) {
        if (otherField == null) {
            otherField = new ConcurrentHashMap<>();
        }
        otherField.put(key, obj);
    }

    public void removeField(String key) {
        otherField.remove(key );
    }
}
