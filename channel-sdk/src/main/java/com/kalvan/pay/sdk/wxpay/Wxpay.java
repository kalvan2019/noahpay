package com.kalvan.pay.sdk.wxpay;
import lombok.Getter;
import lombok.Setter;

import com.alibaba.fastjson.JSONObject;
import com.kalvan.pay.sdk.wxpay.api.DefaultWxpayClient;
import com.kalvan.pay.sdk.wxpay.api.WxpayApiException;
import com.kalvan.pay.sdk.wxpay.api.WxpayClient;
import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.request.auth.*;
import com.kalvan.pay.sdk.wxpay.api.request.miniprogram.WxpayAuthJsCodeToSessionRequest;
import com.kalvan.pay.sdk.wxpay.api.request.miniprogram.WxpaySendMiniProgramHbRequest;
import com.kalvan.pay.sdk.wxpay.api.request.pay.*;
import com.kalvan.pay.sdk.wxpay.api.request.tool.*;
import com.kalvan.pay.sdk.wxpay.api.response.auth.*;
import com.kalvan.pay.sdk.wxpay.api.response.miniprogram.WxpayAuthJsCodeToSessionResponse;
import com.kalvan.pay.sdk.wxpay.api.response.miniprogram.WxpaySendMiniProgramHbResponse;
import com.kalvan.pay.sdk.wxpay.api.response.pay.*;
import com.kalvan.pay.sdk.wxpay.api.response.tool.*;
import com.kalvan.pay.sdk.wxpay.api.rules.WxpayJsApiType;
import com.kalvan.pay.sdk.wxpay.api.util.*;
import com.kalvan.pay.sdk.wxpay.dto.request.*;
import com.kalvan.pay.sdk.wxpay.dto.response.WxMiniProgramPhoneNumber;
import com.kalvan.pay.sdk.wxpay.dto.response.WxMiniProgramUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信支付/授权相关业务
 */
@Component
@Slf4j
public class Wxpay {

    WxpayClient wxpayClient;
    WxpayConfig wxpayConfig;
    @Autowired
    public Wxpay( WxpayConfig wxpayConfig){
        super();
        this.wxpayConfig = wxpayConfig;
        this.wxpayClient = new DefaultWxpayClient( wxpayConfig.appid,wxpayConfig.mchId, wxpayConfig.key, wxpayConfig.secret, wxpayConfig.certFile);
        log.info("build WxpayClient [{}] success.", wxpayClient.getName());
    }

    /**
     * 用户同意授权，获取code
     * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，默认拥有scope参数中的snsapi_base和snsapi_userinfo）
     *
     * @param redirect_uri 授权后重定向的回调链接地址， 已使用 {@code URLEncoder.encode()}对链接进行处理
     * @param scope        应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     * @param state        重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return {@link WxpayAuthGetCodeResponse}
     * @throws UnsupportedEncodingException
     */
    public WxpayAuthGetCodeResponse getCode(String redirect_uri, String scope, String state) throws UnsupportedEncodingException {
        WxpayAuthGetCodeResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("redirect_uri", URLEncoder.encode(redirect_uri, WxpayConstants.CHARSET_UTF8));
        bizContent.put("scope", scope);
        if (!StringUtils.isEmpty(state)) {
            bizContent.put("state", state);
        }
        WxpayAuthGetCodeRequest request = new WxpayAuthGetCodeRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("getCode() error:{}", e);
        }
        return response;
    }

    /**
     * 通过code换取网页授权access_token
     * 首先请注意，这里通过code换取的是一个特殊的网页授权access_token，与基础支持中的access_token（该access_token用于调用其他接口）不同。<br/>
     * 如果网页授权的作用域为snsapi_base，则本方法中获取到网页授权access_token的同时，也获取到了openid。
     *
     * @param code 用户同意授权，获取code
     * @return {@link WxpayAuthCodeInfoResponse}
     */
    public WxpayAuthCodeInfoResponse codeInfo(String code) {
        WxpayAuthCodeInfoResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("code", code);
        WxpayAuthCodeInfoRequest request = new WxpayAuthCodeInfoRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("codeInfo() error:{}", e);
        }
        return response;
    }

    /**
     * 刷新access_token
     *
     * @param refresh_token 通过access_token获取到的refresh_token参数
     * @return {@link WxpayAuthRefreshTokenResponse}
     */
    public WxpayAuthRefreshTokenResponse refreshToken(String refresh_token) {
        WxpayAuthRefreshTokenResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("refresh_token", refresh_token);
        WxpayAuthRefreshTokenRequest request = new WxpayAuthRefreshTokenRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("refreshToken() error:{}", e);
        }
        return response;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param access_token 网页授权接口调用凭证，注意：此access_token与基础支持的access_token不同。
     * @param openid       用户的唯一标识
     * @return {@link WxpayAuthUserInfoResponse}
     */
    public WxpayAuthUserInfoResponse userInfo(String access_token, String openid) {
        WxpayAuthUserInfoResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("access_token", access_token);
        bizContent.put("openid", openid);
        WxpayAuthUserInfoRequest request = new WxpayAuthUserInfoRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("userInfo() error:{}", e);
        }
        return response;
    }

    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param access_token 网页授权接口调用凭证，注意：此access_token与基础支持的access_token不同。
     * @param openid       用户的唯一标识
     * @return {@link WxpayAuthCheckTokenResponse}
     */
    public WxpayAuthCheckTokenResponse checkToken(String access_token, String openid) {
        WxpayAuthCheckTokenResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("access_token", access_token);
        bizContent.put("openid", openid);
        WxpayAuthCheckTokenRequest request = new WxpayAuthCheckTokenRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("checkToken() error:{}", e);
        }
        return response;
    }

    /**
     * 获取access_token
     * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。<br/>
     * 开发者需要进行妥善保存，access_token的存储至少要保留512个字符空间。<br/>
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     *
     * @return {@link WxpayAuthGetTokenResponse}
     */
    public WxpayAuthGetTokenResponse getToken() {
        WxpayAuthGetTokenResponse response = null;
        WxpayAuthGetTokenRequest request = new WxpayAuthGetTokenRequest();
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("getToken() error:{}", e);
        }
        return response;
    }

    /**
     * 获取用户基本信息(UnionID机制)
     *
     * @param access_token 公众号的全局唯一接口调用凭据
     * @param openid       用户的唯一标识
     * @return {@link WxpayAuthUserInfoUnionIDResponse}
     */
    public WxpayAuthUserInfoUnionIDResponse userInfoUnionID(String access_token, String openid) {
        WxpayAuthUserInfoUnionIDResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("access_token", access_token);
        bizContent.put("openid", openid);
        WxpayAuthUserInfoUnionIDRequest request = new WxpayAuthUserInfoUnionIDRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("userInfoUnionID() error:{}", e);
        }
        return response;
    }

    /**
     * 获取 api_ticket临时票据
     * api_ticket 是用于调用微信卡券JS API的临时票据，有效期为7200 秒，通过access_token来获取。
     *
     * @param type         类型  jsapi；wx_card：微信卡券
     * @param access_token 公众号的全局唯一接口调用凭据
     * @return {@link WxpayAuthGetTicketResponse}
     */
    public WxpayAuthGetTicketResponse getTicket(String type, String access_token) {
        WxpayAuthGetTicketResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("type", type);
        bizContent.put("access_token", access_token);
        WxpayAuthGetTicketRequest request = new WxpayAuthGetTicketRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("getTicket() error:{}", e);
        }
        return response;
    }

    /**
     * 小程序登录凭证校验。
     * 通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程
     *
     * @param js_code wx.login 接口获得临时登录凭证 code
     * @return {@link WxpayAuthJsCodeToSessionResponse}
     */
    public WxpayAuthJsCodeToSessionResponse jsCodeToSession(String js_code) {
        WxpayAuthJsCodeToSessionResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("js_code", js_code);
        WxpayAuthJsCodeToSessionRequest request = new WxpayAuthJsCodeToSessionRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.auth(request);
        } catch (WxpayApiException e) {
            log.error("jsCodeToSession() error:{}", e);
        }
        return response;
    }


    /**
     * 统一下单
     * 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易会话标识后再按Native、JSAPI、APP等不同场景生成交易串调起支付。
     *
     * @param unifiedorder 统一下单请求参数 {@link WxpayUnifiedorder}
     * @return {@link WxpayUnifiedorderResponse}
     */
    public WxpayUnifiedorderResponse unifiedorder(WxpayUnifiedorder unifiedorder) {
        WxpayUnifiedorderResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(unifiedorder);
        WxpayUnifiedorderRequest request = new WxpayUnifiedorderRequest();
        request.setBizContent(bizContent.toString());
        request.setNotifyUrl(wxpayConfig.notifyUrl);
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("unifiedOrder() error:{}", e);
        }
        return response;
    }

    /**
     * 查询订单（请求参数二选一）
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。<br/>
     * 需要调用查询接口的情况：
     * <ul>
     * <li>当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；</li>
     * <li>调用支付接口后，返回系统错误或未知交易状态情况；</li>
     * <li>调用付款码支付API，返回USERPAYING的状态；</li>
     * <li>调用关单或撤销接口API之前，需确认支付状态。</li>
     * </ul>
     *
     * @param transaction_id 微信的订单号，建议优先使用
     * @param out_trade_no   商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * @return {@link WxpayOrderQueryResponse}
     */
    public WxpayOrderQueryResponse orderQuery(String transaction_id, String out_trade_no) {
        WxpayOrderQueryResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("transaction_id", transaction_id);
        bizContent.put("out_trade_no", out_trade_no);
        WxpayOrderQueryRequest request = new WxpayOrderQueryRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("orderQuery() error:{}", e);
        }
        return response;
    }

    /**
     * 关闭订单
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。<br/>
     * 以下情况需要调用关单接口：
     * <ul>
     * <li>商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；</li>
     * <li>系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。</li>
     * </ul>
     *
     * @param out_trade_no 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * @return {@link WxpayCloseOrderResponse}
     */
    public WxpayCloseOrderResponse closeOrder(String out_trade_no) {
        WxpayCloseOrderResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        WxpayCloseOrderRequest request = new WxpayCloseOrderRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("closeOrder() error:{}", e);
        }
        return response;
    }

    /**
     * 撤销订单（请求参数二选一）
     * 支付交易返回失败或支付系统超时，调用该接口撤销交易。<br/>
     * 如果此订单用户支付失败，微信支付系统会将此订单关闭；如果用户支付成功，微信支付系统会将此订单资金退还给用户。<br/>
     * 注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
     *
     * @param transaction_id 微信的订单号，建议优先使用
     * @param out_trade_no   商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * @return {@link WxpayReverseResponse}
     */
    public WxpayReverseResponse reverse(String transaction_id, String out_trade_no) {
        WxpayReverseResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("transaction_id", transaction_id);
        bizContent.put("out_trade_no", out_trade_no);
        WxpayReverseRequest request = new WxpayReverseRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("reverse() error:{}", e);
        }
        return response;
    }

    /**
     * 申请退款
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，微信支付将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。</br>
     * <b>服务商模式，退款需要使用服务商的证书，需要服务商在服务商平台授权子商户的退款权限。</b>
     *
     * @param refund 退款请求参数 {@link WxpayRefund}
     * @return {@link WxpayRefundResponse}
     */
    public WxpayRefundResponse refund(WxpayRefund refund) {
        WxpayRefundResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(refund);
        WxpayRefundRequest request = new WxpayRefundRequest();
        request.setNotifyUrl(wxpayConfig.refundNotifyUrl);
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("refund() error:{}", e);
        }
        return response;
    }

    /**
     * 查询退款
     * 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
     *
     * @param refund 退款请求参数 {@link WxpayRefund}
     * @return {@link WxpayRefundResponse}
     */
    public WxpayRefundQueryResponse refundQuery(WxpayRefund refund) {
        WxpayRefundQueryResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(refund);
        WxpayRefundQueryRequest request = new WxpayRefundQueryRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("refundQuery() error:{}", e);
        }
        return response;
    }

    /**
     * 下载对账单
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     *
     * @param bill_date 下载对账单的日期，格式：20140603
     * @param bill_type 账单类型 ALL（默认值），返回当日所有订单信息（不含充值退款订单）
     * @return {@link WxpayDownloadBillResponse}
     */
    public WxpayDownloadBillResponse downloadBill(String bill_date, String bill_type) {
        WxpayDownloadBillResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("bill_date", bill_date);
        bizContent.put("bill_type", bill_type);
        WxpayDownloadBillRequest request = new WxpayDownloadBillRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("downloadBill() error:{}", e);
        }
        return response;
    }

    /**
     * 企业付款
     * 用于企业向微信用户个人付款，目前支持向指定微信用户的openid付款。
     *
     * @param transfers 企业付款请求参数 {@link WxpayTransfers}
     * @return {@link WxpayTransfersResponse}
     */
    public WxpayTransfersResponse transfers(WxpayTransfers transfers) {
        WxpayTransfersResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(transfers);
        WxpayTransfersRequest request = new WxpayTransfersRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("transfers() error:{}", e);
        }
        return response;
    }

    /**
     * 查询企业付款
     * 用于商户的企业付款操作进行结果查询，返回付款操作详细结果。<br/>
     * 查询企业付款API只支持查询30天内的订单，30天之前的订单请登录商户平台查询。
     *
     * @param partner_trade_no 商户调用企业付款API时使用的商户订单号
     * @return {@link WxpayGetTransferInfoResponse}
     */
    public WxpayGetTransferInfoResponse getTransferInfo(String partner_trade_no) {
        WxpayGetTransferInfoResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("partner_trade_no", partner_trade_no);
        WxpayGetTransferInfoRequest request = new WxpayGetTransferInfoRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("getTransferInfo() error:{}", e);
        }
        return response;
    }

    /**
     * 发放普通红包
     *
     * @param sendRedPack 发放普通红包请求参数 {@link WxpaySendRedPack}
     * @return {@link WxpaySendRedPackResponse}
     */
    public WxpaySendRedPackResponse sendRedPack(WxpaySendRedPack sendRedPack) {
        WxpaySendRedPackResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(sendRedPack);
        WxpaySendRedPackRequest request = new WxpaySendRedPackRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("sendRedPack() error:{}", e);
        }
        return response;
    }

    /**
     * 发放裂变红包
     *
     * @param sendGroupRedPack 发放裂变红包请求参数 {@link WxpaySendGroupRedPack}
     * @return {@link WxpaySendGroupRedPackResponse}
     */
    public WxpaySendGroupRedPackResponse sendGroupRedPack(WxpaySendGroupRedPack sendGroupRedPack) {
        WxpaySendGroupRedPackResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(sendGroupRedPack);
        WxpaySendGroupRedPackRequest request = new WxpaySendGroupRedPackRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("sendGroupRedPack() error:{}", e);
        }
        return response;
    }

    /**
     * 发放小程序红包
     *
     * @param sendMiniProgramHb 发放小程序红包请求参数 {@link WxpaySendMiniProgramHb}
     * @return {@link WxpaySendMiniProgramHbResponse}
     */
    public WxpaySendMiniProgramHbResponse sendMiniProgramHb(WxpaySendMiniProgramHb sendMiniProgramHb) {
        WxpaySendMiniProgramHbResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(sendMiniProgramHb);
        WxpaySendMiniProgramHbRequest request = new WxpaySendMiniProgramHbRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("sendMiniProgramHb() error:{}", e);
        }
        return response;
    }

    /**
     * 查询红包记录
     * 用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
     *
     * @param mch_billno 商户发放红包的商户订单号
     * @return {@link WxpayGetHbInfoResponse}
     */
    public WxpayGetHbInfoResponse getHbInfo(String mch_billno) {
        WxpayGetHbInfoResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("mch_billno", mch_billno);
        bizContent.put("bill_type", "MCHT");
        WxpayGetHbInfoRequest request = new WxpayGetHbInfoRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("getHbInfo() error:{}", e);
        }
        return response;
    }

    /**
     * 发放代金券
     *
     * @param sendCoupon 发放代金券请求参数 {@link WxpaySendCoupon}
     * @return {@link WxpaySendCouponResponse}
     */
    public WxpaySendCouponResponse sendCoupon(WxpaySendCoupon sendCoupon) {
        WxpaySendCouponResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(sendCoupon);
        WxpaySendCouponRequest request = new WxpaySendCouponRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("sendCoupon() error:{}", e);
        }
        return response;
    }

    /**
     * 查询代金券批次
     *
     * @param coupon_stock_id 代金券批次id
     * @param op_user_id      操作员帐号, 默认为商户号
     * @param device_info     微信支付分配的终端设备号
     * @return {@link WxpayQueryCouponStockResponse}
     */
    public WxpayQueryCouponStockResponse queryCouponStock(String coupon_stock_id, String op_user_id, String device_info) {
        WxpayQueryCouponStockResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("coupon_stock_id", coupon_stock_id);
        if (!StringUtils.isEmpty(op_user_id)) {
            bizContent.put("op_user_id", op_user_id);
        }
        if (!StringUtils.isEmpty(device_info)) {
            bizContent.put("device_info", device_info);
        }
        WxpayQueryCouponStockRequest request = new WxpayQueryCouponStockRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("getTransferInfo() error:{}", e);
        }
        return response;
    }

    /**
     * 查询代金券信息
     *
     * @param queryCouponsInfo 查询代金券批次请求参数 {@link WxpayQueryCouponsInfo}
     * @return {@link WxpayQueryCouponsInfoResponse}
     */
    public WxpayQueryCouponsInfoResponse queryCouponInfo(WxpayQueryCouponsInfo queryCouponsInfo) {
        WxpayQueryCouponsInfoResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(queryCouponsInfo);
        WxpayQueryCouponsInfoRequest request = new WxpayQueryCouponsInfoRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("queryCouponInfo() error:{}", e);
        }
        return response;
    }

    /**
     * 交易保障
     * 商户在调用微信支付提供的相关接口时，会得到微信支付返回的相关信息以及获得整个接口的响应时间。<br/>
     * 为提高整体的服务水平，协助商户一起提高服务质量，微信支付提供了相关接口调用耗时和返回信息的主动上报接口，微信支付可以根据商户侧上报的数据进一步优化网络部署，完善服务监控，和商户更好的协作为用户提供更好的业务体验。
     *
     * @param report 交易保障请求参数 {@link WxpayReport}
     * @return {@link WxpayReportResponse}
     */
    public WxpayReportResponse report(WxpayReport report) {
        WxpayReportResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(report);
        WxpayReportRequest request = new WxpayReportRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("report() error:{}", e);
        }
        return response;
    }

    /**
     * 转换短链接
     * 该接口主要用于Native支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)，减小二维码数据量，提升扫描速度和精确度。
     *
     * @param long_url 需要转换的URL，签名用原串，传输需URLencode
     * @return {@link WxpayShortUrlResponse}
     * @throws UnsupportedEncodingException
     */
    public WxpayShortUrlResponse shortUrl(String long_url) throws UnsupportedEncodingException {
        WxpayShortUrlResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("long_url", URLEncoder.encode(long_url, WxpayConstants.CHARSET_UTF8));
        WxpayShortUrlRequest request = new WxpayShortUrlRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("shortUrl() error:{}", e);
        }
        return response;
    }

    /**
     * 提交付款码支付
     * 收银员使用扫码设备读取微信用户付款码以后，二维码或条码信息会传送至商户收银台，由商户收银台或者商户后台调用该接口发起支付。
     *
     * @param micropay 提交付款码支付请求参数 {@link WxpayMicropay}
     * @return {@link WxpayMicropayResponse}
     */
    public WxpayMicropayResponse micropay(WxpayMicropay micropay) {
        WxpayMicropayResponse response = null;
        JSONObject bizContent = (JSONObject) JSONObject.toJSON(micropay);
        WxpayMicropayRequest request = new WxpayMicropayRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("report() error:{}", e);
        }
        return response;
    }

    /**
     * 授权码查询openid
     * 通过授权码查询公众号Openid，调用查询后，该授权码只能由此商户号发起扣款，直至授权码更新。
     *
     * @param auth_code 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     * @return {@link WxpayAuthCodeToOpenidResponse}
     */
    public WxpayAuthCodeToOpenidResponse authCodeToOpenid(String auth_code) {
        WxpayAuthCodeToOpenidResponse response = null;
        JSONObject bizContent = new JSONObject();
        bizContent.put("auth_code", auth_code);
        WxpayAuthCodeToOpenidRequest request = new WxpayAuthCodeToOpenidRequest();
        request.setBizContent(bizContent.toString());
        try {
            response = wxpayClient.execute(request);
        } catch (WxpayApiException e) {
            log.error("authCodeToOpenid() error:{}", e);
        }
        return response;
    }

    /**
     * 支付结果通知
     * 支付完成后，微信会把相关支付结果及用户信息通过数据流的形式发送给商户，商户需要接收处理，并按文档规范返回应答。
     *
     * @param in 数据流  {@code ServletInputStream in = HttpServletRequest.getInputStream();}
     * @return {@link WxpayPayNotifyResponse}
     */
    public WxpayPayNotifyResponse payNotify(InputStream in) {
        WxpayPayNotifyResponse response = null;
        WxpayPayNotifyRequest request = new WxpayPayNotifyRequest();
        try {
            String xmlStr = StreamUtils.readText(in, WxpayConstants.CHARSET_UTF8);
            WxpayHashMap hashMap = WxpayUtils.xmlToMap(xmlStr);
            request.setNotifyData(JSONObject.toJSONString(hashMap));
            response = wxpayClient.notify(request);
        } catch (Exception e) {
            log.error("payNotify() error:{}", e);
        }
        return response;
    }

    /**
     * 退款结果通知
     * 当商户申请的退款有结果后，微信会把相关结果发送给商户，商户需要接收处理，并返回应答。
     *
     * @param in 数据流 {@code ServletInputStream in = HttpServletRequest.getInputStream();}
     * @return {@link WxpayRefundNotifyResponse}
     */
    public WxpayRefundNotifyResponse refundNotify(InputStream in) {
        WxpayRefundNotifyResponse response = null;
        WxpayRefundNotifyRequest request = new WxpayRefundNotifyRequest();
        try {
            String xmlStr = StreamUtils.readText(in, WxpayConstants.CHARSET_UTF8);
            WxpayHashMap hashMap = WxpayUtils.xmlToMap(xmlStr);
            request.setNotifyData(JSONObject.toJSONString(hashMap));
            response = wxpayClient.notify(request);
        } catch (Exception e) {
            log.error("payNotify() error:{}", e);
        }
        return response;
    }

    /**
     * 退款结果通知加密信息
     *
     * @param refundNotify 退款结果通知 {@link WxpayRefundNotifyResponse}
     * @return {@link WxpayRefundNotifyReqInfoResponse}
     */
    public WxpayRefundNotifyReqInfoResponse refundNotifyReqInfo(WxpayRefundNotifyResponse refundNotify) {
        WxpayRefundNotifyReqInfoResponse response = null;
        if (refundNotify != null) {
            if (refundNotify.isSuccess()) {
                String key = wxpayConfig.key;
				/*if (!StringUtils.isEmpty(wxpayConfig.subKey()) && !StringUtils.isEmpty(wxpayConfig.subMchId())) {
					key = wxpayConfig.subKey();
				}*/
                try {
                    String reqInfo = WxpayUtils.decryptRefundNotifyData(refundNotify.getReqInfo(), key);
                    WxpayHashMap reqInfoMap = WxpayUtils.xmlToMap(reqInfo);
                    response = WxpayUtils.mapToObject(reqInfoMap, WxpayRefundNotifyReqInfoResponse.class);
                } catch (Exception e) {
                    log.error("refundNotifyReqInfo() error:{}", e);
                }
            }
        }
        return response;
    }

    /**
     * 商户处理后同步返回应答（支付/退款结果通知）
     *
     * @param return_code 返回状态码 SUCCESS/FAIL，SUCCESS表示商户接收通知成功并校验成功
     * @param return_msg  返回信息 OK，如非空，为错误原因：签名失败，参数格式校验错误
     * @param response    {@link HttpServletResponse}
     */
    public void notifyReturn(String return_code, String return_msg, HttpServletResponse response) {
        String respXml = "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
        try {
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(respXml.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("notifyReturn() error:{}", e);
        }
    }

    /**
     * 微信内H5调起支付（公众号支付、小程序支付）
     * 在微信浏览器里面打开H5网页中执行JS调起支付。接口输入输出数据格式为JSON。</br>
     * 在微信小程序调用wx.requestPayment(OBJECT)发起微信支付。服务商模式下应为当前调起支付小程序的appid。</br>
     *
     * @param prepay_id 预支付交易会话标识
     * @return {@link JSONObject}
     */
    public JSONObject getBrandWCPayRequest(String prepay_id) {
        JSONObject request = new JSONObject();
        WxpayHashMap params = new WxpayHashMap();
        String appId = wxpayConfig.appid;
        // 小程序发起支付
        if (!StringUtils.isEmpty(wxpayConfig.subAppid)
                && wxpayConfig.subAppid.startsWith(WxpayConstants.MINI)) {
            appId = wxpayConfig.subAppid.split(WxpayConstants.MINI)[1];
        }
        params.put("appId", appId);
        params.put("timeStamp", WxpayUtils.getCurrentTimestamp());
        params.put("nonceStr", WxpayUtils.generateNonceStr());
        params.put("package", "prepay_id=" + prepay_id);
        params.put("signType", WxpayConstants.SIGN_TYPE_MD5);
        try {
            params.put("paySign", WxpaySignature.generateSignature(params, wxpayConfig.key, WxpayConstants.SIGN_TYPE_MD5));
            request = (JSONObject) JSONObject.toJSON(params);
        } catch (Exception e) {
            log.error("getBrandWCPayRequest()  paySign error:{}", e);
        }
        return request;
    }

    /**
     * JS-SDK注入权限验证配置
     * 1、先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。<br/>
     * 2、在需要调用JS接口的页面引入如下JS文件，（支持https）：http://res.wx.qq.com/open/js/jweixin-1.4.0.js
     *
     * @param url          当前网页的URL，不包含#及其后面部分
     * @param jsapi_ticket 公众号用于调用微信JS接口的临时票据
     * @param jsApiType    接口类型 {@link WxpayJsApiType}
     * @return {@link WxpayJsApiConfigResponse}
     */
    public WxpayJsApiConfigResponse jsApiConfig(String jsapi_ticket, String url, WxpayJsApiType jsApiType) {
        WxpayJsApiConfigResponse response = null;
        JSONObject signatureData = new JSONObject();
        signatureData.put(WxpayConstants.JS_API_TYPE, jsApiType);
        signatureData.put("url", url.split("#")[0]);
        signatureData.put("jsapi_ticket", jsapi_ticket);
        signatureData.put("noncestr", WxpayUtils.generateNonceStr());
        signatureData.put("timestamp", WxpayUtils.getCurrentTimestamp());
        WxpayJsApiConfigRequest request = new WxpayJsApiConfigRequest();
        request.setSignatureData(signatureData.toString());
        try {
            response = wxpayClient.jsApi(request);
        } catch (WxpayApiException e) {
            log.error("jsApiConfig() error:{}", e);
        }
        return response;
    }

    /**
     * 小程序获取用户信息
     * encryptedData和iv数据需要转义传输</br>原生页面使用 {@code encodeURIComponent(encryptedData)}、{@code encodeURIComponent(iv)}
     *
     * @param sessionKey    用户的 session-key
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return {@link WxMiniProgramUserInfo}
     * @throws UnsupportedEncodingException
     */
    public WxMiniProgramUserInfo getUserInfo(String sessionKey, String encryptedData, String iv) throws UnsupportedEncodingException {
        WxMiniProgramUserInfo miniProgramUserInfo = new WxMiniProgramUserInfo();
        try {
            String userinfo = WxpayEncrypt.userInfoDecrypt(sessionKey,
                    new String(encryptedData.getBytes("ISO-8859-1"), WxpayConstants.CHARSET_UTF8),
                    new String(iv.getBytes("ISO-8859-1"), WxpayConstants.CHARSET_UTF8));
            log.info(userinfo);
            JSONObject userInfoJSON = JSONObject.parseObject(userinfo);
            miniProgramUserInfo = JSONObject.toJavaObject(userInfoJSON, WxMiniProgramUserInfo.class);
        } catch (WxpayApiException e) {
            log.error("getUserInfo() error:{}", e);
        }
        return miniProgramUserInfo;
    }

    /**
     * 小程序获取用户手机号
     * encryptedData和iv数据需要转义传输</br>原生页面使用 {@code encodeURIComponent(encryptedData)}、{@code encodeURIComponent(iv)}
     *
     * @param sessionKey    用户的 session-key
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return {@link WxMiniProgramPhoneNumber}
     * @throws UnsupportedEncodingException
     */
    public WxMiniProgramPhoneNumber getPhoneNumber(String sessionKey, String encryptedData, String iv) throws UnsupportedEncodingException {
        WxMiniProgramPhoneNumber miniProgramPhoneNumber = new WxMiniProgramPhoneNumber();
        try {
            String phoneNumber = WxpayEncrypt.userInfoDecrypt(sessionKey,
                    new String(encryptedData.getBytes("ISO-8859-1"), WxpayConstants.CHARSET_UTF8),
                    new String(iv.getBytes("ISO-8859-1"), WxpayConstants.CHARSET_UTF8));
            JSONObject phoneNumberJSON = JSONObject.parseObject(phoneNumber);
            miniProgramPhoneNumber = JSONObject.toJavaObject(phoneNumberJSON, WxMiniProgramPhoneNumber.class);
        } catch (WxpayApiException e) {
            log.error("getPhoneNumber() error:{}", e);
        }
        return miniProgramPhoneNumber;
    }

}
