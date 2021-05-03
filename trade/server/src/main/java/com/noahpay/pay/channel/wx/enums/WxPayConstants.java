package com.noahpay.pay.channel.wx.enums;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.HttpClient;

/**
 * 微信基础常量
 */
@Getter
@Setter
public class WxPayConstants {

    public static final String VERSION = "version";

    public static final String WXPAY = "wxpay";

    public static final String WXPAYAUTH = "wxpayauth";

    public static final String REQUEST = "request";

    public static final String SUCCESS = "SUCCESS";

    public static final String OK = "OK";

    public static final String FAIL = "FAIL";

    public static final String ERRCODE = "errcode";

    public static final String ERRMSG = "errmsg";

    public static final String RETURN_CODE = "return_code";

    public static final String RETURN_MSG = "return_msg";

    public static final String DATA = "data";

    public static final String LEFT_ANGLE_BRACKETS = "<";

    public static final String GET_BIZ_CONTENT_METHOD = "getBizContent";

    public static final String GET_NOTIFY_DATA_METHOD = "getNotifyData";

    public static final String GET_SIGNATURE_DATA_METHOD = "getSignatureData";

    public static final String CODE_URL = "code_url";

    public static final String ENCRYPT_TYPE_AES = "AES";

    public static final String ALGORITHM_AES_MODE_PADDING = "AES/ECB/PKCS7Padding";

    public static final String ALGORITHM_AES_MODE_PADDING2 = "AES/CBC/PKCS7Padding";

    public static final String ALGORITHM_AES_PROVIDER = "BC";

    public static final String ENCRYPT_TYPE_MD5 = "MD5";

    public static final String ENCRYPT_TYPE_HMACSHA256 = "HmacSHA256";

    public static final String TRADE_TYPE = "trade_type";

    public static final String APPID = "appid";

    public static final String MSGAPPID = "msgappid";

    public static final String SUB_APPID = "sub_appid";

    public static final String WXAPPID = "wxappid";

    public static final String MCH_ID = "mch_id";

    public static final String SUB_MCH_ID = "sub_mch_id";

    public static final String MCH_APPID = "mch_appid";

    public static final String MCHID = "mchid";

    public static final String OPENID = "openid";

    public static final String TIMESTAMP = "timestamp";

    public static final String ATTACH = "attach";

    public static final String NONCE_STR = "nonce_str";

    public static final String SIGN = "sign";

    public static final String SIGN_TYPE = "sign_type";

    public static final String SIGN_TYPE_MD5 = "MD5";

    public static final String NOTIFY_URL = "notify_url";

    public static final String BIZ_CONTENT = "biz_content";

    public static final String NOTIFY_DATA = "notify_data";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIMEZONE = "GMT+8";

    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String FORMAT_JSON = "json";

    public static final String FORMAT_XML = "xml";

    public static final String HTTPS = "https://";

    public static final String HTTP = "http://";

    public static final String RESPONSE_TYPE = "response_type";

    public static final String CODE = "code";

    public static final String SECRET = "secret";

    public static final String GRANT_TYPE = "grant_type";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String LANG = "lang";

    public static final String JS_API_TYPE = "type";

    public static final String SIGNATURE_DATA = "signature_data";

    public static final String NONCESTR = "noncestr";

    public static final String USER_AGENT =
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
                    ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    public static final String MINI = "mini:";

	/*--------------------------------------------------------------------------------------------------
		-------------------------------------- 微信业务 API --------------------------------------
	--------------------------------------------------------------------------------------------------*/

    public static final String DOMAIN_API = HTTPS + "api.weixin.qq.com";

    public static final String DOMAIN_API_MCH = HTTPS + "api.mch.weixin.qq.com";

    public static final String DOMAIN_MP = HTTPS + "mp.weixin.qq.com";

    public static final String DOMAIN_OPEN = HTTPS + "open.weixin.qq.com";

    public static final String BIZ_REQUEST_URL_SUFFIX = "_URL_SUFFIX";

    /*--------------------------------- 微信订单 ---------------------------------------------*/
    public static final String MICROPAY_URL_SUFFIX = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX = "/pay/closeorder";

    /*--------------------------------- 订单退款 ---------------------------------------------*/
    public static final String REFUND_URL_SUFFIX = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX = "/pay/refundquery";

    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    /*--------------------------------- 现金红包 ---------------------------------------------*/
    public static final String SENDREDPACK_URL_SUFFIX = "/mmpaymkttransfers/sendredpack";
    public static final String SENDGROUPREDPACK_URL_SUFFIX = "/mmpaymkttransfers/sendgroupredpack";
    public static final String SENDMINIPROGRAMHB_URL_SUFFIX = "/mmpaymkttransfers/sendminiprogramhb";
    public static final String GETHBINFO_URL_SUFFIX = "/mmpaymkttransfers/gethbinfo";

    /*--------------------------------- 企业付款 ---------------------------------------------*/
    public static final String TRANSFERS_URL_SUFFIX = "/mmpaymkttransfers/promotion/transfers";
    public static final String GETTRANSFERINFO_URL_SUFFIX = "/mmpaymkttransfers/gettransferinfo";


    /*--------------------------------- 发放代金券 -------------------------------------------*/
    public static final String SENDCOUPON_URL_SUFFIX = "/mmpaymkttransfers/send_coupon";
    public static final String QUERYCOUPONSTOCK_URL_SUFFIX = "/mmpaymkttransfers/query_coupon_stock";
    public static final String QUERYCOUPONSINFO_URL_SUFFIX = "/mmpaymkttransfers/querycouponsinfo";

    /*--------------------------------- 微信授权 ---------------------------------------------*/
    public static final String GETCODE_URL_SUFFIX = "/connect/oauth2/authorize";
    public static final String CODEINFO_URL_SUFFIX = "/sns/oauth2/access_token";
    public static final String REFRESHTOKEN_URL_SUFFIX = "/sns/oauth2/refresh_token";
    public static final String USERINFO_URL_SUFFIX = "/sns/userinfo";
    public static final String CHECKTOKEN_URL_SUFFIX = "/sns/auth";
    public static final String JSCODETOSESSION_URL_SUFFIX = "/sns/jscode2session";

    public static final String USERINFOUNIONID_URL_SUFFIX = "/cgi-bin/user/info";
    public static final String GETTOKEN_URL_SUFFIX = "/cgi-bin/token";
    public static final String GETTICKET_URL_SUFFIX = "/cgi-bin/ticket/getticket";

    public static final String QRCODECREATETICKET_URL_SUFFIX = "/cgi-bin/qrcode/create";
    public static final String SHOWQRCODE_URL_SUFFIX = "/cgi-bin/showqrcode";
    public static final String GETCALLBACK_IP_URL_SUFFIX = "/cgi-bin/getcallbackip";
    public static final String CALLBACKCHECK_URL_SUFFIX = "/cgi-bin/callback/check";

    public static final String MENUCREATE_URL_SUFFIX = "/cgi-bin/menu/create";
    public static final String MENUGET_URL_SUFFIX = "/cgi-bin/menu/get";
    public static final String MENUDELETE_URL_SUFFIX = "/cgi-bin/menu/delete";

}
