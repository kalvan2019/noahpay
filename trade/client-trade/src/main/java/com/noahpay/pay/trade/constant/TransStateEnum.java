package com.noahpay.pay.trade.constant;

/**
 * 交易状态
 *
 * @author chenliang
 */
public enum TransStateEnum {
    /**
     * 交易状态
     * 扩展细节状态CommonState
     */
    SUCCESS(0, "交易成功"),
    FAIL(1, "交易失败"),
    ACCEPT(2, "已受理"),
    CONFIRM(3, "交易待验证"),
    OVERTIME(4, "交易超时"),
    QUERY_FAIL(5, "查询无记录"),
    WAIT(10, "待支付"),
    ASYNC_WAIT_PROCESS(11, "异步处理登记"),
    ASYNC_IN_PROCESS(12, "异步处理中"),
    ROUTE(13, "路由完成"),
    FEE(14, "计费完成"),
    SEND(15, "已发往通道"),
    SUCCESS_PROCESS(16, "交易成功异步入账"),
    FAIL_PROCESS(17, "交易失败异步冲正"),
    FAIL_CLOSE(18, "订单关闭"),
    REFUND_ING(19, "退款处理中"),
    REFUND_SUCCESS(20, "已退款"),
    ;
    public int code;
    public String desc;

    TransStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
