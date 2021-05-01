package com.noahpay.pay.channel.constant;

/**
 * @author chenliang
 */
public interface ExtParamKey {
    /**
     * 扩展参数-通道商户名称 KEY
     */
    String MERCHANT_NAME_KEY = "merchantName";
    /**
     * 扩展参数-通道商户MD5KEY
     */
    String MD5_KEY = "md5Key";
    /**
     * 扩展参数-公钥路径
     */
    String PBL_PATH = "pblPath";
    /**
     * 扩展参数-公钥字符串
     */
    String PBL_STR = "pblStr";
    /**
     * 扩展参数-私钥路径
     */
    String PFX_PATH = "pfxPath";
    /**
     * 扩展参数-私钥字符串
     */
    String PFX_STR = "pfxStr";
    /**
     * 扩展参数-私钥证书密码
     */
    String PFX_PWD = "pfxPwd";
    /**
     * 扩展参数-代付请求地址
     */
    String DF_PAY_URL = "dfPayUrl";
    /**
     * 扩展参数-代付分润请求地址
     */
    String DF_PROFIT_PAY_URL = "dfProfitPayUrl";
    /**
     * 扩展参数-扫码查询请求地址
     */
    String QUERY_QRPAY_URL = "queryQrPayUrl";
    /**
     * 扩展参数-收单查询请求地址
     */
    String QUERY_ORDER_URL = "queryOrderUrl";
    /**
     * 扩展参数-签约查询请求地址
     */
    String QUERY_SIGN_URL = "querySignUrl";
    /**
     * 扩展参数-代付查询请求地址
     */
    String QUERY_DF_URL = "queryDfUrl";
    /**
     * 扩展参数-代付分润查询请求地址
     */
    String QUERY_DF_PROFIT_URL = "queryDfProfitUrl";
    /**
     * 扩展参数-余额查询请求地址
     */
    String QUERY_BALANCE_URL = "queryBalanceUrl";
    /**
     * 扩展参数-子商户余额查询请求地址
     */
    String QUERY_SUB_BALANCE_URL = "querySubBalanceUrl";
    /**
     * 扩展参数-退款查询请求地址
     */
    String QUERY_REFUND_URL = "queryRefundUrl";
    /**
     * 扩展参数-快捷支付申请请求地址
     */
    String FAST_PAY_APPLY_URL = "fastPayApplyUrl";
    /**
     * 扩展参数-快捷支付请求地址(金运通)
     */
    String FAST_PAY_FAST_URL = "fastPayFastUrl";
    /**
     * 扩展参数-快捷确认支付请求地址
     */
    String FAST_PAY_URL = "fastPayUrl";
    /**
     * 扩展参数-网关支付请求地址
     */
    String GATEWAY_PAY_URL = "gatewayPayUrl";
    /**
     * 扩展参数-回调前缀地址
     */
    String CALL_BACK_URL = "callBackUrl";
    /**
     * 扩展参数-签约申请请求地址
     */
    String SIGN_APPLY_URL = "signApplyUrl";
    /**
     * 扩展参数-签约确认请求地址
     */
    String SIGN_CONFIRM_URL = "signConfirmUrl";
    /**
     * 扩展参数-　快捷签约申请请求地址
     */
    String SIGN_APPLY_FAST_URL = "signApplyFastUrl";
    /**
     * 扩展参数-快捷签约确认请求地址
     */
    String SIGN_CONFIRM_FAST_URL = "signConfirmFastUrl";
    /**
     * 扩展参数-被动解约通知地址
     */
    String SIGN_PASSIVE_CANCEL_URL = "signPassiveCancelUrl";
    /**
     * 扩展参数-聚合支付请求地址
     */
    String AGGREGATE_URL = "aggregateUrl";
    /**
     * 扩展参数-聚合支付通知地址
     */
    String AGGREGATE_NOTIFY_URL = "aggregateNotifyUrl";
    /**
     * 扩展参数-签约申请自己包装H5请求地址
     */
    String SIGN_APPLY_H5_URL = "signApplyH5Url";
    /**
     * 扩展参数-鉴权请求地址
     */
    String AUT_URL = "autUrl";
    /**
     * 扩展参数-图片上传地址
     */
    String IMAGE_UPLOAD_URL = "imageUploadUrl";
    /**
     * 扩展参数-二维码支付地址
     */
    String QR_PAY_URL = "qrPayUrl";
    /**
     * 扩展参数-商户进件地址
     */
    String MER_REGISTER_URL = "merRegisterUrl";
    /**
     * 扩展参数-二维码消费支付地址
     */
    String QR_CODE_TRANS_URL = "qrCodeTransUrl";
    /**
     * 扩展参数-银联签约绑卡商户
     */
    String UN_SIGN_MER_ID = "unSignMerId";
    /**
     * 扩展参数-注册客户号地址
     */
    String CUST_REGISTER_URL = "custRegisterUrl";
    /**
     * 扩展参数-退款请求地址
     */
    String REFUND_URL = "refundUrl";
    /**
     * 扩展参数-交通银行加签验签xml文件的绝对路径
     */
    String XML_CONFIG_PATH = "xmlConfigPath";
    /**
     * 扩展参数-网关支付查询
     */
    String QUERY_GATEWAY_PAY_URL = "queryGatewayPayUrl";
}
