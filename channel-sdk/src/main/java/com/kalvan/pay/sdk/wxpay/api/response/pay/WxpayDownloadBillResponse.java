package com.kalvan.pay.sdk.wxpay.api.response.pay;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay downloadbill response
 */
@Getter
@Setter
public class WxpayDownloadBillResponse extends WxpayResponse {

    private static final long serialVersionUID = 5822703842125393251L;

    /**
     * 成功时，数据以文本表格的方式返回，第一行为表头，后面各行为对应的字段内容，字段内容跟查询订单或退款结果一致，具体字段说明可查阅相应接口。
     */
    private String data;
    /**
     * 失败错误码
     */
    private String errorCode;

}
