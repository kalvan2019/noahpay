package com.noahpay.pay.trade.constant;

/**
 * @author kalvan
 */
public enum NotifyStateEnum {
    /**
     * 通知状态
     */
    NOTIFY_SUCCESS(0, "通知成功"),
    NOTIFY_FAIL(1, "通知失败"),
    NOTIFY_WAIT(2, "等待通知"),
    NOTIFY_URL_ERROR(3, "通知地址错误"),
    NOTIFY_BLACKLIST(4, "黑名单地址"),
    ;
    public int code;
    public String desc;

    NotifyStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
