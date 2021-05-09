import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.noahpay.pay.TradeApplication;
import com.noahpay.pay.channel.wx.WxClient;
import com.noahpay.pay.channel.wx.WxRequest;
import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.request.WxPayUnifiedOrder;
import com.noahpay.pay.channel.wx.response.pay.WxPayUnifiedorderResponse;
import com.noahpay.pay.commons.db.trade.mapper.PayBillMapper;
import com.noahpay.pay.trade.bean.req.OrderRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.constant.PayTypeEnum;
import com.noahpay.pay.trade.iface.IPayTrans;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author chenliang
 */
@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = {TradeApplication.class})
public class TradeApplicationTest {

    private static OrderRequest orderRequest;
    private static Response<TransResponse> transResponse;
    @Resource
    PayBillMapper payBillMapper;
    @Resource
    IPayTrans iPayTrans;
//    @Autowired
//    private WxPay wxpay;

    @Test
    public void testValidate() {
        OrderRequest request = new OrderRequest();
        BeanValidationResult validate = ValidationUtil.warpValidate(request);
        if (!validate.isSuccess()) {
            throw new BizException(CommonReturnCode.PARAM_ILLEGAL.formatMessage(validate.getErrorMessages().stream().map(BeanValidationResult.ErrorMessage::getMessage).collect(Collectors.joining(","))));
        }
    }

    public static void main(String[] args) {
        WxPayUnifiedOrder unifiedOrder = new WxPayUnifiedOrder();
        unifiedOrder.setBody("测试商品名称");
//        unifiedOrder.setDetail();
        unifiedOrder.setAttach("{\"biller_code\":\"123456\",\"reference number\":\"1001\"}");
        unifiedOrder.setOut_trade_no("DD201907101400846");
        unifiedOrder.setTotal_fee("1");
        unifiedOrder.setFee_type("USD");
        unifiedOrder.setSpbill_create_ip("127.0.0.1");
//        unifiedOrder.setTime_start();
//        unifiedOrder.setTime_expire();
        unifiedOrder.setNotify_url("http://www.baidu.com");
        unifiedOrder.setTrade_type(PayTypeEnum.NATIVE.code);
//        unifiedOrder.setProduct_id();
//        unifiedOrder.setOpenid();
//        unifiedOrder.setSub_openid();
//        unifiedOrder.setDevice_info();
//        unifiedOrder.setReceipt();
        unifiedOrder.setAppid("wxce926ea78004260e");
        unifiedOrder.setMch_id("1900012291");
//        unifiedOrder.setSub_appid();
        unifiedOrder.setSub_mch_id("11611403");

        WxRequest wxRequest = new WxRequest(unifiedOrder, WxPayUnifiedorderResponse.class, WxPayConstants.UNIFIEDORDER_URL_SUFFIX).setKey("2ab9071b06b9f739b950ddb41db2690d");
        System.out.println(wxRequest.getResponseClass());
        WxPayUnifiedorderResponse response = WxClient.execute(wxRequest);
        if (response.isSuccess()) {
            // 微信业务请求成功
            // 统一下单成功
            // 自己的业务代码
            System.out.println("xxx");
        }
    }


//    @Test
//    public void testUnifiedorder() {
//        WxpayUnifiedorder unifiedorder = new WxpayUnifiedorder();
//        unifiedorder.setBody("测试商品名称");
//        unifiedorder.setOut_trade_no("DD201907101400845");
//        unifiedorder.setTotal_fee("1");
//        unifiedorder.setFee_type("USD");
//        unifiedorder.setTrade_type(WxpayTradeType.NATIVE);
//        unifiedorder.setSpbill_create_ip("127.0.0.1");
//        unifiedorder.setAttach("{\"biller_code\":\"123456\",\"reference number\":\"1001\"}");
//        unifiedorder.setSub_mch_id("11611403");
//        WxPayUnifiedorderResponse response = wxpay.unifiedOrder(unifiedorder);
//        if (response.isSuccess()) { // 微信业务请求成功
//            // 统一下单成功
//            // 自己的业务代码
//            wxpay.orderQuery(response.getPrepayId(), null);
//            wxpay.orderQuery(null, unifiedorder.getOut_trade_no());
//        }
//    }
}
