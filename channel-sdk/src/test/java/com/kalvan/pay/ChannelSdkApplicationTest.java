package com.kalvan.pay;

import com.kalvan.pay.sdk.wxpay.Wxpay;
import com.kalvan.pay.sdk.wxpay.api.request.pay.WxpayOrderQueryRequest;
import com.kalvan.pay.sdk.wxpay.api.response.pay.WxpayUnifiedorderResponse;
import com.kalvan.pay.sdk.wxpay.api.rules.WxpayTradeType;
import com.kalvan.pay.sdk.wxpay.dto.request.WxpayUnifiedorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenliang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChannelSdkApplication.class)
public class ChannelSdkApplicationTest {
    @Autowired
    private Wxpay wxpay;

    @Test
    public void testUnifiedorder() {
        WxpayUnifiedorder unifiedorder = new WxpayUnifiedorder();
        unifiedorder.setBody("测试商品名称");
        unifiedorder.setOut_trade_no("DD201907101400845");
        unifiedorder.setTotal_fee("1");
        unifiedorder.setFee_type("USD");
        unifiedorder.setTrade_type(WxpayTradeType.NATIVE);
        unifiedorder.setSpbill_create_ip("127.0.0.1");
        unifiedorder.setAttach("{\"biller_code\":\"123456\",\"reference number\":\"1001\"}");
        unifiedorder.setSub_mch_id("11611403");
        WxpayUnifiedorderResponse response = wxpay.unifiedorder(unifiedorder);
        if (response.isSuccess()) { // 微信业务请求成功
            // 统一下单成功
            // 自己的业务代码
            wxpay.orderQuery(response.getPrepayId(), null);
            wxpay.orderQuery(null, unifiedorder.getOut_trade_no());
        }
    }

    @Test
    public void testQueryOrder() {
        WxpayOrderQueryRequest orderQueryRequest = new WxpayOrderQueryRequest();

    }
}
