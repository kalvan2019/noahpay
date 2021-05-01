package com.kalvan.pay.sdk.wxpay.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.kalvan.pay.sdk.wxpay.api.request.auth.*;
import com.kalvan.pay.sdk.wxpay.api.request.miniprogram.WxpayAuthJsCodeToSessionRequest;
import com.kalvan.pay.sdk.wxpay.api.request.miniprogram.WxpaySendMiniProgramHbRequest;
import com.kalvan.pay.sdk.wxpay.api.request.pay.WxpayRefundQueryRequest;
import com.kalvan.pay.sdk.wxpay.api.request.pay.WxpayRefundRequest;
import com.kalvan.pay.sdk.wxpay.api.request.tool.WxpayGetTransferInfoRequest;
import com.kalvan.pay.sdk.wxpay.api.request.tool.WxpaySendGroupRedPackRequest;
import com.kalvan.pay.sdk.wxpay.api.request.tool.WxpaySendRedPackRequest;
import com.kalvan.pay.sdk.wxpay.api.request.tool.WxpayTransfersRequest;
import com.kalvan.pay.sdk.wxpay.api.rules.WxpayAuthGrantType;
import com.kalvan.pay.sdk.wxpay.api.util.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * abstract wxpay client
 *
 * @version 1.3.0
 */
@Slf4j
public abstract class AbstractWxpayClient implements WxpayClient {

    /**
     * 客户端名称
     */
    private String name = this.getClass().getSimpleName();
    /**
     * 公众账号ID 服务商的APPID
     */
    private String appId;
    /**
     * 子商户公众账号ID
     */
    private String subAppId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 子商户号
     */
    private String subMchId;
    /**
     * 商户秘钥
     */
    private String key;
    /**
     * 子商户秘钥
     */
    private String subKey;
    /**
     * 应用秘钥
     **/
    private String secret;
    /**
     * 安全证书
     */
    private String certFile;
    /**
     * 子商户安全证书
     */
    private String subCertFile;
    /**
     * 服务请求url
     */
    private String serverUrl;
    /**
     * 数据格式，默认json
     */
    private String format = WxpayConstants.FORMAT_JSON;
    /**
     * 编码集，默认UTF-8
     */
    private String charset = WxpayConstants.CHARSET_UTF8;
    /**
     * 签名方式，默认MD5
     */
    private String signType = WxpayConstants.SIGN_TYPE_MD5;
    /**
     * 连接超时毫秒
     */
    private int connectTimeout = 3000;
    /**
     * 读取超时毫秒
     */
    private int readTimeout = 15000;


    public AbstractWxpayClient(String name) {
        super();
        this.name = name;
    }

    /**
     * 默认网页授权客户端
     *
     * @param appId
     * @param secret
     */
    public AbstractWxpayClient(String appId, String secret) {
        super();
        this.appId = appId;
        this.secret = secret;
    }

    public AbstractWxpayClient(String appId, String secret, int connectTimeout, int readTimeout) {
        super();
        this.appId = appId;
        this.secret = secret;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public AbstractWxpayClient(String appId, String mchId, String key) {
        super();
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
    }

    /**
     * 默认普通商户支付客户端
     *
     * @param appId
     * @param subAppId
     * @param mchId
     * @param subMchId
     * @param key
     * @param secret
     * @param certFile
     */
    public AbstractWxpayClient(String appId, String mchId, String key, String secret, String certFile) {
        super();
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
        this.secret = secret;
        this.certFile = certFile;
    }

    /**
     * 默认服务商支付客户端
     *
     * @param appId
     * @param subAppId
     * @param mchId
     * @param subMchId
     * @param key
     * @param secret
     * @param certFile
     */
    public AbstractWxpayClient(String appId, String subAppId, String mchId, String subMchId, String key, String subKey, String secret, String certFile, String subCertFile) {
        super();
        this.appId = appId;
        this.subAppId = subAppId;
        this.mchId = mchId;
        this.subMchId = subMchId;
        this.key = key;
        this.subKey = subKey;
        this.secret = secret;
        this.certFile = certFile;
        this.subCertFile = subCertFile;
    }


    public AbstractWxpayClient(String appId, String subAppId, String mchId, String subMchId, String key, String secret,
                               String certFile, String format, String charset, String signType) {
        super();
        this.appId = appId;
        this.subAppId = subAppId;
        this.mchId = mchId;
        this.subMchId = subMchId;
        this.key = key;
        this.secret = secret;
        this.certFile = certFile;
        if (StringUtils.isEmpty(format)) {
            format = WxpayConstants.FORMAT_JSON;
        }
        this.format = format;
        if (StringUtils.isEmpty(charset)) {
            charset = WxpayConstants.CHARSET_UTF8;
        }
        this.charset = charset;
        this.signType = signType;
    }

    public AbstractWxpayClient(String appId, String mchId, String key, String secret,
                               String certFile, String format, String charset, String signType,
                               int connectTimeout, int readTimeout) {
        super();
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
        this.secret = secret;
        this.certFile = certFile;
        if (StringUtils.isEmpty(format)) {
            format = WxpayConstants.FORMAT_JSON;
        }
        this.format = format;
        if (StringUtils.isEmpty(charset)) {
            charset = WxpayConstants.CHARSET_UTF8;
        }
        this.charset = charset;
        this.signType = signType;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public AbstractWxpayClient(String appId, String subAppId, String mchId, String subMchId, String key, String secret,
                               String certFile, String format, String charset, String signType,
                               int connectTimeout, int readTimeout) {
        super();
        this.appId = appId;
        this.subAppId = subAppId;
        this.mchId = mchId;
        this.subMchId = subMchId;
        this.key = key;
        this.secret = secret;
        this.certFile = certFile;
        if (StringUtils.isEmpty(format)) {
            format = WxpayConstants.FORMAT_JSON;
        }
        this.format = format;
        if (StringUtils.isEmpty(charset)) {
            charset = WxpayConstants.CHARSET_UTF8;
        }
        this.charset = charset;
        this.signType = signType;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public <T extends WxpayAuthResponse> T auth(WxpayAuthRequest<T> request) throws WxpayApiException {
        T tRsp = null;
        WxpayHashMap appParams = new WxpayHashMap(request.getTextParams());
        JSONObject bizContent = new JSONObject();
        try {
            if (request instanceof WxpayAuthGetTokenRequest) {
                bizContent.put(WxpayConstants.GRANT_TYPE, WxpayAuthGrantType.client_credential);
                bizContent.put(WxpayConstants.SECRET, this.secret);
            } else {
                if (request.getClass().getMethod(WxpayConstants.GET_BIZ_CONTENT_METHOD) != null
                        && !StringUtils.isEmpty(appParams.get(WxpayConstants.BIZ_CONTENT))) {
                    try {
                        bizContent = JSONObject.parseObject(appParams.get(WxpayConstants.BIZ_CONTENT));
                    } catch (JSONException e) {
                        throw new WxpayApiException("biz content data format error", e);
                    }
                }
            }
        } catch (NoSuchMethodException | SecurityException e) {
            throw new WxpayApiException("an unexpected error", e);
        }
        bizContent.put(WxpayConstants.APPID, this.appId);
        String requestUrl = request.serverDomain() + getRequestUrlSuffix(request);
        this.serverUrl = requestUrl;
        if (request instanceof WxpayAuthGetCodeRequest) {
            bizContent.put(WxpayConstants.RESPONSE_TYPE, WxpayConstants.CODE);
            StringBuilder sb = new StringBuilder(requestUrl);
            sb.append("?");
            Map<String, String> appMap = WxpayUtils.jsonToMap(bizContent.toString());
            String params = WxpayUtils.getSortedParams(appMap);
            sb.append(params);
            sb.append("#wechat_redirect");
            JSONObject respJSON = new JSONObject();
            respJSON.put(WxpayConstants.CODE_URL, sb.toString());
            respJSON.put(WxpayConstants.ERRCODE, "0");
            respJSON.put(WxpayConstants.ERRMSG, WxpayConstants.OK);
            tRsp = JSONObject.toJavaObject(respJSON, request.getResponseClass());
            tRsp.setParams(appMap);
            return tRsp;
        } else if (request instanceof WxpayAuthCodeInfoRequest) {
            bizContent.put(WxpayConstants.SECRET, this.secret);
            bizContent.put(WxpayConstants.GRANT_TYPE, WxpayAuthGrantType.authorization_code);
        } else if (request instanceof WxpayAuthUserInfoRequest ||
                request instanceof WxpayAuthUserInfoUnionIDRequest) {
            bizContent.remove(WxpayConstants.APPID);
            bizContent.put(WxpayConstants.LANG, "zh_CN");
        } else if (request instanceof WxpayAuthCheckTokenRequest ||
                request instanceof WxpayAuthGetTicketRequest) {
            bizContent.remove(WxpayConstants.APPID);
        } else if (request instanceof WxpayAuthRefreshTokenRequest) {
            bizContent.put(WxpayConstants.GRANT_TYPE, WxpayAuthGrantType.refresh_token);
        } else if (request instanceof WxpayAuthJsCodeToSessionRequest) {
            bizContent.put(WxpayConstants.SECRET, this.secret);
            bizContent.put(WxpayConstants.GRANT_TYPE, WxpayAuthGrantType.authorization_code);
        }
        Map<String, String> appMap = WxpayUtils.jsonToMap(bizContent.toString());
        String requestParams = WxpayUtils.getSortedParams(appMap);
        String result = "";
        log.debug("[{}] {} request params: {}", this.getName(), request.getClass().getSimpleName(), requestParams);
        try {
            result = WebUtils.requestGet(request.serverDomain(), getRequestUrlSuffix(request), requestParams, this.connectTimeout, this.readTimeout);
        } catch (Exception e) {
            throw new WxpayApiException(requestUrl + "the request failed", e);
        }
        log.debug("[{}] {} response result: {}", this.getName(), request.getClass().getSimpleName(), result);
        tRsp = JSONObject.toJavaObject(JSONObject.parseObject(result), request.getResponseClass());
        tRsp.setParams(appMap);
        return tRsp;
    }

    @Override
    public <T extends WxpayResponse> T execute(WxpayRequest<T> request) throws WxpayApiException {
        T tRsp = null;
        String requestUrl = request.serverDomain() + getRequestUrlSuffix(request);
        RequestParametersHolder parametersHolder = getRequestHolderWithSign(request, this.key, this.subKey);
        String requestParams = getXmlParameters(parametersHolder);
        this.serverUrl = requestUrl;
        String result = "";
        String requestCertFile = getRequestCertFile(request);
        String requestMchId = this.mchId;
        try {
            log.debug("[{}] {} request params: {}", this.getName(), request.getClass().getSimpleName(), requestParams);
            if (request.isNeedCert()) {
                if (!StringUtils.isEmpty(this.subAppId) && !StringUtils.isEmpty(this.subMchId)) {
                    if (!(request instanceof WxpayRefundRequest)) {
                        requestMchId = this.subMchId;
                    }
                }
                result = WebUtils.requestWithCert(request.serverDomain(), getRequestUrlSuffix(request), requestParams, this.getConnectTimeout(), this.getReadTimeout(), requestMchId, requestCertFile);
            } else {
                result = WebUtils.requestWithoutCert(request.serverDomain(), getRequestUrlSuffix(request), requestParams, this.getConnectTimeout(), this.getReadTimeout());
            }
        } catch (Exception e) {
            throw new WxpayApiException(requestUrl + "the request failed", e);
        }
        log.debug("[{}] {} response result: {}", this.getName(), request.getClass().getSimpleName(), result);
        WxpayHashMap resultMap = new WxpayHashMap();
        if (!StringUtils.isEmpty(result)) {
            try {
                if (result.indexOf(WxpayConstants.LEFT_ANGLE_BRACKETS) == 0) {
                    resultMap = WxpayUtils.xmlToMap(result);
                } else {
                    // 对账单下载
                    resultMap.put(WxpayConstants.RETURN_CODE, WxpayConstants.SUCCESS);
                    resultMap.put(WxpayConstants.RETURN_MSG, WxpayConstants.OK);
                    resultMap.put(WxpayConstants.DATA, result);
                }
                tRsp = WxpayUtils.mapToObject(resultMap, request.getResponseClass());
                tRsp.setParams(parametersHolder.getApplicationParams());
            } catch (Exception e) {
                throw new WxpayApiException("an unexpected error", e);
            }
        }
        return tRsp;
    }

    @Override
    public <T extends WxpayResponse> T notify(WxpayRequest<T> request) throws WxpayApiException {
        T tRsp = null;
        WxpayHashMap appParams = new WxpayHashMap(request.getTextParams());
        try {
            if (request.getClass().getMethod(WxpayConstants.GET_NOTIFY_DATA_METHOD) != null
                    && !StringUtils.isEmpty(appParams.get(WxpayConstants.NOTIFY_DATA))) {
                if (request.isCheckSign()) {
                    if (!checkResponseSign(appParams)) {
                        throw new WxpayApiException("check sign failed");
                    }
                }
                String notifyData = appParams.get(WxpayConstants.NOTIFY_DATA);
                Map<String, String> respMap = WxpayUtils.jsonToMap(notifyData);
                tRsp = WxpayUtils.mapToObject(respMap, request.getResponseClass());
                tRsp.setParams(respMap);
            }
        } catch (Exception e) {
            throw new WxpayApiException("an unexpected error", e);
        }
        return tRsp;
    }

    @Override
    public <T extends WxpayJsApiResponse> T jsApi(WxpayJsApiRequest<T> request) throws WxpayApiException {
        T tRsp = null;
        WxpayHashMap appParams = new WxpayHashMap(request.getTextParams());
        try {
            if (request.getClass().getMethod(WxpayConstants.GET_SIGNATURE_DATA_METHOD) != null
                    && !StringUtils.isEmpty(appParams.get(WxpayConstants.SIGNATURE_DATA))) {
                String signatureData = appParams.get(WxpayConstants.SIGNATURE_DATA);
                Map<String, Object> respMap = WxpayUtils.jsonToMapObject(signatureData);
                if (!respMap.containsKey(WxpayConstants.JS_API_TYPE)) {
                    throw new WxpayApiException("missing [type] parameters");
                }
                String jsApiType = String.valueOf(respMap.get(WxpayConstants.JS_API_TYPE));
                respMap.remove(WxpayConstants.JS_API_TYPE);
                String signature = WxpaySignature.generateJsapiSignature(respMap);
                tRsp = WxpayUtils.mapToObject(respMap, request.getResponseClass());
                tRsp.setAppId(this.appId);
                tRsp.setSignature(signature);
                tRsp.setNonceStr(String.valueOf(respMap.get(WxpayConstants.NONCESTR)));
                tRsp.setJsApiList(WxpayUtils.getJsApiList(jsApiType));
                tRsp.setParams(respMap);
            }
        } catch (Exception e) {
            throw new WxpayApiException("an unexpected error", e);
        }
        return tRsp;
    }

    /**
     * 获取xml字串参数
     *
     * @param requestParametersHolder
     * @return xml字串参数
     * @throws WxpayApiException
     */
    private String getXmlParameters(RequestParametersHolder requestParametersHolder) throws WxpayApiException {
        String xmlParameters = "";
        try {
            xmlParameters = WxpayUtils.mapToXml(requestParametersHolder.getApplicationParams());
        } catch (Exception e) {
            throw new WxpayApiException("map to xml failed", e);
        }
        return xmlParameters;
    }

    /**
     * 获取带签名的请求参数体
     *
     * @param request
     * @param key
     * @return
     * @throws WxpayApiException
     */
    private <T extends WxpayResponse> RequestParametersHolder getRequestHolderWithSign(WxpayRequest<?> request, String key, String subKey) throws WxpayApiException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        WxpayHashMap appParams = new WxpayHashMap(request.getTextParams());
        JSONObject bizContent = new JSONObject();
        try {
            if (request.getClass().getMethod(WxpayConstants.GET_BIZ_CONTENT_METHOD) != null
                    && !StringUtils.isEmpty(appParams.get(WxpayConstants.BIZ_CONTENT))) {
                try {
                    bizContent = JSONObject.parseObject(appParams.get(WxpayConstants.BIZ_CONTENT));
                } catch (JSONException e) {
                    throw new WxpayApiException("biz content data format error", e);
                }
            }
        } catch (NoSuchMethodException | SecurityException e) {
            throw new WxpayApiException("an unexpected error", e);
        }
        if (request instanceof WxpayTransfersRequest
                || request instanceof WxpayGetTransferInfoRequest) {
            if (!StringUtils.isEmpty(this.subAppId) && !StringUtils.isEmpty(this.subMchId)) {
                bizContent.put(WxpayConstants.MCH_APPID, this.subAppId);
                bizContent.put(WxpayConstants.MCHID, this.subMchId);
                key = subKey;
            } else {
                bizContent.put(WxpayConstants.MCH_APPID, this.appId);
                bizContent.put(WxpayConstants.MCHID, this.mchId);
            }
        } else if (request instanceof WxpaySendRedPackRequest
                || request instanceof WxpaySendGroupRedPackRequest
                || request instanceof WxpaySendMiniProgramHbRequest) {
            bizContent.put(WxpayConstants.WXAPPID, this.appId);
            bizContent.put(WxpayConstants.MCH_ID, this.mchId);
            if (request instanceof WxpaySendRedPackRequest
                    || request instanceof WxpaySendGroupRedPackRequest) {
                if (!StringUtils.isEmpty(this.subAppId) && !StringUtils.isEmpty(this.subMchId)) {
                    bizContent.put(WxpayConstants.MSGAPPID, this.subAppId);
                    bizContent.put(WxpayConstants.SUB_MCH_ID, this.subMchId);
                    key = subKey;
                }
            }
        } else if (request instanceof WxpayRefundRequest
                || request instanceof WxpayRefundQueryRequest) {
            if (!StringUtils.isEmpty(this.subAppId) && !StringUtils.isEmpty(this.subMchId)) {
                bizContent.put(WxpayConstants.APPID, this.appId);
                bizContent.put(WxpayConstants.MCH_ID, this.mchId);
                // 小程序退款处理
                if (this.subAppId.startsWith(WxpayConstants.MINI)) {
                    bizContent.put(WxpayConstants.SUB_APPID, this.subAppId.split(WxpayConstants.MINI)[1]);
                }
                // bizContent.put(WxpayConstants.SUB_APPID, this.subAppId);
                bizContent.put(WxpayConstants.SUB_MCH_ID, this.subMchId);
                // key = subKey;
            }
            if (!StringUtils.isEmpty(this.signType)) {
                bizContent.put(WxpayConstants.SIGN_TYPE, this.signType);
            }
        } else {
            bizContent.put(WxpayConstants.APPID, this.appId);
            bizContent.put(WxpayConstants.MCH_ID, this.mchId);
            if (!StringUtils.isEmpty(this.subAppId) && !StringUtils.isEmpty(this.subMchId)) {
                // bizContent.put(WxpayConstants.SUB_APPID, this.subAppId);
                // 小程序支付处理
                if (this.subAppId.startsWith(WxpayConstants.MINI)) {
                    bizContent.put(WxpayConstants.SUB_APPID, this.subAppId.split(WxpayConstants.MINI)[1]);
                }
                bizContent.put(WxpayConstants.SUB_MCH_ID, this.subMchId);
            }
            if (!StringUtils.isEmpty(this.signType)) {
                bizContent.put(WxpayConstants.SIGN_TYPE, this.signType);
            }
        }
        bizContent.put(WxpayConstants.NONCE_STR, WxpayUtils.generateNonceStr());
        if (!StringUtils.isEmpty(request.getNotifyUrl())) {
            bizContent.put(WxpayConstants.NOTIFY_URL, request.getNotifyUrl());
        }
        String sign = "";
        try {
            sign = WxpaySignature.generateSignature(WxpayUtils.jsonToMap(bizContent.toString()), key, this.signType);
        } catch (Exception e) {
            throw new WxpayApiException("failed to generate signature", e);
        }
        bizContent.put(WxpayConstants.SIGN, sign);

        Map<String, String> appMap = WxpayUtils.jsonToMap(bizContent.toString());
        requestHolder.setApplicationParams(new WxpayHashMap(appMap));
        return requestHolder;
    }

    /**
     * 获取并匹配请求url后缀
     *
     * @param request
     * @return 请求url后缀
     * @throws WxpayApiException
     */
    private String getRequestUrlSuffix(WxpayRequest<?> request) throws WxpayApiException {
        String requestUrlSuffix = "";
        if (request != null) {
            String requestClassName = request.getClass().getSimpleName().toLowerCase();
            String bizName = requestClassName.replace(WxpayConstants.WXPAY, "").replace(WxpayConstants.REQUEST, "");
            Field field = null;
            try {
                field = WxpayConstants.class.getField(bizName.toUpperCase() + WxpayConstants.BIZ_REQUEST_URL_SUFFIX);
                requestUrlSuffix = String.valueOf(field.get(new WxpayConstants()));
            } catch (Exception e) {
                throw new WxpayApiException("an unexpected error", e);
            }
        }
        return requestUrlSuffix;
    }

    /**
     * 获取并匹配证书文件
     *
     * @param request
     * @return 请求url后缀
     * @throws WxpayApiException
     */
    private String getRequestCertFile(WxpayRequest<?> request) {
        String requestCertFile = this.certFile;
        if (request != null) {
            if (!StringUtils.isEmpty(this.subAppId) && !StringUtils.isEmpty(this.subMchId) && !StringUtils.isEmpty(this.subCertFile)) {
                if (!(request instanceof WxpayRefundRequest)) {
                    requestCertFile = this.subCertFile;
                }
            }
        }
        return requestCertFile;
    }

    /**
     * 获取并匹配请求url后缀
     *
     * @param request
     * @return 请求url后缀
     * @throws WxpayApiException
     */
    private String getRequestUrlSuffix(WxpayAuthRequest<?> request) throws WxpayApiException {
        String requestUrlSuffix = "";
        if (request != null) {
            String requestClassName = request.getClass().getSimpleName().toLowerCase();
            String bizName = requestClassName.replace(WxpayConstants.WXPAYAUTH, "").replace(WxpayConstants.REQUEST, "");
            Field field = null;
            try {
                field = WxpayConstants.class.getField(bizName.toUpperCase() + WxpayConstants.BIZ_REQUEST_URL_SUFFIX);
                requestUrlSuffix = String.valueOf(field.get(new WxpayConstants()));
            } catch (Exception e) {
                throw new WxpayApiException("an unexpected error", e);
            }
        }
        return requestUrlSuffix;
    }

    /**
     * 验证响应签名
     *
     * @param appParams
     * @return true/false
     * @throws WxpayApiException
     */
    private boolean checkResponseSign(WxpayHashMap appParams) throws WxpayApiException {
        boolean isChecked = false;
        String data = appParams.get(WxpayConstants.NOTIFY_DATA);
        Map<String, String> map = WxpayUtils.jsonToMap(data);
        try {
            isChecked = WxpaySignature.isSignatureValid(map, this.key, this.signType);
        } catch (Exception e) {
            throw new WxpayApiException("failed to check response signature", e);
        }
        return isChecked;
    }

    @Override
    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCertFile() {
        return certFile;
    }

    public void setCertFile(String certFile) {
        this.certFile = certFile;
    }

    public String getSubCertFile() {
        return subCertFile;
    }

    public void setSubCertFile(String subCertFile) {
        this.subCertFile = subCertFile;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if (StringUtils.isEmpty(format)) {
            format = WxpayConstants.FORMAT_JSON;
        }
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        if (StringUtils.isEmpty(charset)) {
            charset = WxpayConstants.CHARSET_UTF8;
        }
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public String getName() {
        return name;
    }

    public AbstractWxpayClient setName(String name) {
        this.name = name;
        return this;
    }

}
