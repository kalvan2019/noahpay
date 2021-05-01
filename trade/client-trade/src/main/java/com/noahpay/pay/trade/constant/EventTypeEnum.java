package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum EventTypeEnum {
    /**
     * 事件类型定义
     */
    ACCOUNT_IN(1, "入账，必须成功"),
    ACCOUNT_OUT(2, "出账，必须成功"),
    ACCOUNT_UNDO(3, "冲正,必须要做成功"),
    OVERTIME(4, "交易通道超时,查询补偿"),
    ACCEPT(5, "交易通道已受理,查询补偿"),
    EXCEPTION_NOTIFY(6, "通知MQ失败"),
    CONFIRM(7, "交易待验证"),
    ;
    public int code;
    public String desc;

    EventTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
