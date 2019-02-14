
package com.kingyon.chengxin.insurance.dto.qixindto;
import com.kingyon.chengxin.framework.dto.BaseDto;
import com.kingyon.chengxin.insurance.dto.qxapidto.ProductInsuredJob;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2018-08-02 13:37:24
 */
@Data
public class QxData extends BaseDto {

    private String transNo;
    private long partnerId;
    private List<QxProduct> products;
    private List<ProductInsuredJob> jobs = new ArrayList<>();
    private Object orderDetail;
}