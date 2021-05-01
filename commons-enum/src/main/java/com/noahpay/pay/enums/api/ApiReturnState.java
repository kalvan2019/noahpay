package com.noahpay.pay.enums.api;


/**
 * @author chenliang
 */
public enum ApiReturnState {
    /**
     * 状态控制
     */
    SUCCESS(0, "交易成功"),
    PROCESS(1, "交易处理中,请等通知或者查询订单"),
    PROCESS_CONFIRM(2, "交易待验证"),
    FAIL(3, "交易失败"),
    FAIL_CLOSE(4, "交易关闭");

    public int state;
    public String desc;

    ApiReturnState(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }
}