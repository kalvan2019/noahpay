package com.noahpay.pay.cust.bean.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 客户信息查询
 *
 * @author chenliang
 */
@Getter
@Setter
public class CustQueryRequest implements Serializable {
    /**
     * 客户号
     */
    private Long custNo;
    /**
     * 证件号
     */
    private String certificateNo;
}
