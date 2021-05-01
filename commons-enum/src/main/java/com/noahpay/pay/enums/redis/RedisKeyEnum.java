package com.noahpay.pay.enums.redis;


/**
 * redis缓存key
 *
 * @author kalvan
 */
public enum RedisKeyEnum {
    /**
     * api
     */
    API_FREQ_SYSTEM("api_freq_system", "api接口请求频率过高前缀(api使用)"),
    API_FREQ_SERVICE("api_freq_%s", "api接口请求频率缓存前缀(api使用)"),
    API_FREQ_APP_SERVICE("api_freq_app_%s_%s", "api接口请求频率缓存前缀(api使用)"),
    API_FREQ_APP_SERVICE_MONITOR("api_freq_app_%s_%s_monitor", "api接口请求频率监控(api使用)"),
    API_UPDATE("api_update", "系统升级不受理交易(api使用)"),
    /**
     * base
     */
    BASE_ERROR_CARD_BIN("base_error_card_bin_%s", "卡bin识别"),
    /**
     * notify
     */
    NOTIFY_URL_BLACK_LIST("notify_url_black_list_%s_%s_%s", "通知地址黑名单");

    public String key;
    public String desc;

    RedisKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}
