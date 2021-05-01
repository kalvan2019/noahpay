package com.noahpay.pay.channel.constant;

/**
 * @author chenliang
 */
public interface ActionName {
    /**
     * 签约回调
     */
    String SIGN_CALL_BACK = "signCallback";
    /**
     * 代付回调
     */
    String DF_CALL_BACK = "dfCallBack";
    /**
     * 代收回调
     */
    String DS_CALL_BACK = "dsCallBack";
    /**
     * 网关回调
     */
    String GATEWAY_CALL_BACK = "gatewayCallBack";
    /**
     * 快捷回调
     */
    String FAST_CALL_BACK = "quickCallBack";
    /**
     * 扫码回调
     */
    String RQ_CODE_CALL_BACK = "qrCodeCallBack";
    /**
     * 商户进件回调
     */
    String MER_REGISTER_CALL_BACK = "merRegisterCallBack";
}
