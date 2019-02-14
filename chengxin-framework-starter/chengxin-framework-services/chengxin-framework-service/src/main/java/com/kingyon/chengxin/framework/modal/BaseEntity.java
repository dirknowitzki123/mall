package com.kingyon.chengxin.framework.modal;


import com.kingyon.chengxin.framework.AbstractEntity;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Time: 14:02
 */
@Data
public class BaseEntity extends AbstractEntity {

    protected Long id;
    protected Date createTime;
    protected Byte deleted;
    protected Date modifyTime;
    //protected String remarks;


    public BaseEntity() {
        createTime = new Date();
        modifyTime = new Date();
        deleted = 0;
//        remarks = "";
    }

}
