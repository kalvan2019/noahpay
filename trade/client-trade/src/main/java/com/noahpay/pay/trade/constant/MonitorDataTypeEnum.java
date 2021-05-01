package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum MonitorDataTypeEnum {
    /**
     * job任务每5秒查询的数据种类
     */
    DAY_COUNT(0, "日所有数据"),
    DAY_AMOUNT(1, "日所有金额总数"),
    DAY_SUCCESS_COUNT(2, "日交易成功的数据"),
    DAY_SUCCESS_AMOUNT(3, "日交易成功的金额总数"),
    DAY_FAIL_COUNT(4, "日交易失败的数据"),
    DAY_FAIL_AMOUNT(5, "日交易失败的金额总数"),
    DAY_PROCESSED_COUNT(6, "日处理中的数据"),
    DAY_PROCESSED_AMOUNT(7, "日处理中的金额总数"),
    SECONDS_COUNT_5(8, "五秒内数据"),
    SECONDS_COUNT_60(9, "60秒内数据"),
    FAIL_REASON(10, "交易失败原因"),
    ORGAN(11, "机构分布"),
    FAIL_REASON_COUNT(12, "交易失败原因总笔数"),
    ORGAN_COUNT(13, "机构分布总笔数"),
    CHANNEL_SYSTEM_BUSY(14, "通道系统繁忙"),
    TRADE_SYSTEM_BUSY(15, "交易系统繁忙"),
    MEMBER_SYSTEM_BUSY(16, "客户系统繁忙"),
    ACCOUNT_SYSTEM_BUSY(17, "账户系统繁忙"),
    RISK_SYSTEM_BUSY(18, "风控系统繁忙"),
    ROUTE_SYSTEM_BUSY(19, "路由系统繁忙"),
    CONNECTION_CHANNEL_TIMEOUT(20, "连接通道超时"),
    CONNECTION_TIMEOUT(21, "连接超时"),
    SERVICE_OVERRUN_RISK_LIMIT(22, "计费失败手续费超过风控值"),
    SERVICE_UPSIDE_DOWN(23, "计费失败子商户手续费发生倒挂"),
    NO_FEE_RULE(24, "计费接口异常 无可用计费规则"),
    UNBOUND_FEE_TEMPLATE(25, "未绑定计费模板"),
    ORDER_AMOUNT_UNMATCHED_AMOUNT_INTERVAL(26, "计费接口异常 订单金额没有匹配到金额区间"),
    DAY_TRADE_OVERRUN_LIMIT(27, "风控阻断 {'dayUseLimitAmount':'日交易金额超过限额'}"),
    MONTH_TRADE_OVERRUN_LIMIT(28, "风控阻断 {'monthUseLimitNumber':'月交易笔数超过限额'}"),
    NO_ROUTE_AVAILABLE(29, "无可用路由"),
    SYSTEM_EXCEPTION(30, "系统异常"),
    CHANNEL_RECEIPT_EXCEPTION(31, "通道回执异常"),
    CHANNEL_TRANS_FAIL(32, "通道失败分析"),
    SYSTEM_TRANS_FAIL(33, "内部失败分析"),
    SUBMERCHANT_NOT_OPENED(34, "子商户未开通"),
    CARD_BINDING_PROTOCOL(35, "用户绑卡协议"),
    ROUTING_RULE(36, "路由规则配置"),
    DATE_ALLOTMENT_FULL(37, "日分配额度已满"),
    TOTAL_ALLOTMENT_FULL(38, "总分配额度已满"),
    ;
    public int code;
    public String desc;

    MonitorDataTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}