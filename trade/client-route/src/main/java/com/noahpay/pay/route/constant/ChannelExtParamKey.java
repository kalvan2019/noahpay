package com.noahpay.pay.route.constant;

/**
 * 通道配置扩展参数
 *
 * @author chenliang
 */
public enum ChannelExtParamKey {
    /**
     *
     */
    MERCHANT_NAME("merchantName", "通道商户名称"),
    APP_ID("appId", "公众账号ID"),
    API_KEY("apiKey", "签名密钥"),
    NOTIFY_URL("notifyUrl", "通知地址,后面拼接/通道号"),
    PRIVATE_KEY("privateKey", "通道商户私钥"),
    PRIVATE_KEY_PATH("privateKeyPath", "通道商户私钥路径"),
    PRIVATE_KEY_PASSWORD("privateKeyPassword", "通道商户私钥密码"),
    PUBLIC_KEY("publicKey", "公钥"),
    PUBLIC_KEY_PATH("publicKeyPath", "公钥路径"),
    ;

    /**
     * 扩展参数-代付请求地址
     */
    public static final String DF_PAY_URL = "dfPayUrl";
    /**
     * 扩展参数-代付分润请求地址
     */
    public static final String DF_PROFIT_PAY_URL = "dfProfitPayUrl";
    /**
     * 扩展参数-扫码查询请求地址
     */
    public static final String QUERY_QRPAY_URL = "queryQrPayUrl";
    /**
     * 扩展参数-收单查询请求地址
     */
    public static final String QUERY_ORDER_URL = "queryOrderUrl";
    /**
     * 扩展参数-签约查询请求地址
     */
    public static final String QUERY_SIGN_URL = "querySignUrl";
    /**
     * 扩展参数-代付查询请求地址
     */
    public static final String QUERY_DF_URL = "queryDfUrl";
    /**
     * 扩展参数-代付分润查询请求地址
     */
    public static final String QUERY_DF_PROFIT_URL = "queryDfProfitUrl";
    /**
     * 扩展参数-余额查询请求地址
     */
    public static final String QUERY_BALANCE_URL = "queryBalanceUrl";
    /**
     * 扩展参数-子商户余额查询请求地址
     */
    public static final String QUERY_SUB_BALANCE_URL = "querySubBalanceUrl";
    /**
     * 扩展参数-退款查询请求地址
     */
    public static final String QUERY_REFUND_URL = "queryRefundUrl";
    /**
     * 扩展参数-快捷支付申请请求地址
     */
    public static final String FAST_PAY_APPLY_URL = "fastPayApplyUrl";
    /**
     * 扩展参数-快捷支付请求地址(金运通)
     */
    public static final String FAST_PAY_FAST_URL = "fastPayFastUrl";
    /**
     * 扩展参数-快捷确认支付请求地址
     */
    public static final String FAST_PAY_URL = "fastPayUrl";
    /**
     * 扩展参数-网关支付请求地址
     */
    public static final String GATEWAY_PAY_URL = "gatewayPayUrl";
    /**
     * 扩展参数-回调前缀地址
     */
    public static final String CALL_BACK_URL = "callBackUrl";
    /**
     * 扩展参数-签约申请请求地址
     */
    public static final String SIGN_APPLY_URL = "signApplyUrl";
    /**
     * 扩展参数-签约确认请求地址
     */
    public static final String SIGN_CONFIRM_URL = "signConfirmUrl";
    /**
     * 扩展参数-　快捷签约申请请求地址
     */
    public static final String SIGN_APPLY_FAST_URL = "signApplyFastUrl";
    /**
     * 扩展参数-快捷签约确认请求地址
     */
    public static final String SIGN_CONFIRM_FAST_URL = "signConfirmFastUrl";
    /**
     * 扩展参数-被动解约通知地址
     */
    public static final String SIGN_PASSIVE_CANCEL_URL = "signPassiveCancelUrl";
    /**
     * 扩展参数-聚合支付请求地址
     */
    public static final String AGGREGATE_URL = "aggregateUrl";
    /**
     * 扩展参数-聚合支付通知地址
     */
    public static final String AGGREGATE_NOTIFY_URL = "aggregateNotifyUrl";
    /**
     * 扩展参数-签约申请自己包装H5请求地址
     */
    public static final String SIGN_APPLY_H5_URL = "signApplyH5Url";
    /**
     * 扩展参数-鉴权请求地址
     */
    public static final String AUT_URL = "autUrl";
    /**
     * 扩展参数-图片上传地址
     */
    public static final String IMAGE_UPLOAD_URL = "imageUploadUrl";
    /**
     * 扩展参数-二维码支付地址
     */
    public static final String QR_PAY_URL = "qrPayUrl";
    /**
     * 扩展参数-商户进件地址
     */
    public static final String MER_REGISTER_URL = "merRegisterUrl";
    /**
     * 扩展参数-二维码消费支付地址
     */
    public static final String QR_CODE_TRANS_URL = "qrCodeTransUrl";
    /**
     * 扩展参数-银联签约绑卡商户
     */
    public static final String UN_SIGN_MER_ID = "unSignMerId";
    /**
     * 扩展参数-注册客户号地址
     */
    public static final String CUST_REGISTER_URL = "custRegisterUrl";
    /**
     * 扩展参数-退款请求地址
     */
    public static final String REFUND_URL = "refundUrl";
    /**
     * 扩展参数-交通银行加签验签xml文件的绝对路径
     */
    public static final String XML_CONFIG_PATH = "xmlConfigPath";
    /**
     * 扩展参数-网关支付查询
     */
    public static final String QUERY_GATEWAY_PAY_URL = "queryGatewayPayUrl";
    public String code;
    public String desc;

    ChannelExtParamKey(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}