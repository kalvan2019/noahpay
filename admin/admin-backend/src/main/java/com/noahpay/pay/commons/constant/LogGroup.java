package com.noahpay.pay.commons.constant;

/**
 * 操作日志组定义(扩展)
 *
 * @author kalvan
 */
public interface LogGroup {
    /**
     * 鉴权认证
     */
    String AUTH = "auth" ;
    /**
     * 角色日志组<br>
     */
    String ROLE = "role" ;
    /**
     * 管理员日志组<br>
     */
    String ADMIN = "admin" ;
    /**
     * 菜单日志组<br>
     */
    String MENU = "menu" ;
    /**
     * 日志组<br>
     */
    String LOG = "log" ;
    /**
     * 数据字典配置日志组<br>
     */
    String DICT = "dict";
    /**
     * 测试流水表日志组<br>
     */
    String DEMO = "demo";
    /**
     * 接入方信息日志组<br>
     */
    String APP_INFO = "app_info";
    /**
     * 服务发布日志组<br>
     */
    String GATEWAY_SERVICE = "gateway_service";
    /**
     * 接入方服务授权表日志组<br>
     */
    String GATEWAY_AUTHORIZE = "gateway_authorize";
    /**
     * 网关路由表日志组<br>
     */
    String GATEWAY_ROUTE = "gateway_route";
    /**
     * 网关统计日志组<br>
     */
    String GATEWAY_METRIC = "gateway_metric";
    /**
     * 灰度规则配置日志组<br>
     */
    String GRAY_RULE = "gray_rule";
    /**
     * 灰度服务配置日志组<br>
     */
    String GRAY_SERVICE = "gray_service";
    /**
     * 审核信息日志组<br>
     */
    String AUDIT_INFO = "audit_info";
    /**
     * 商户信息日志组
     */
    String MERCHANT_INFO = "merchant_info";
    /**
     * 渠道列表日志组
     */
    String CHANNEL_INFO = "channel_info";
    /**
     * 账户钱包日志组
     */
    String ACCOUNT_INFO = "account_info";
    /**
     * 商户交易额度累计日志组
     */
    String MERCHANT_TRANS_SUM = "merchant_trans_sum";
    /**
     * 交易订单日志组
     */
    String TRANS_BILL = "trans_bill";
    /**
     * 商户计费配置日志组
     */
    String FEE_MERCHANT = "fee_merchant";
    /**
     * 商户路由配置日志组
     */
    String ROUTE_MERCHANT = "route_merchant";
    /**
     * 计费规则配置日志组
     */
    String FEE_RULE = "fee_rule";
    /**
     * 分段计费规则配置日志组
     */
    String FEE_SEGMENT_RULE = "fee_segment_rule";
    /**
     * 路由规则配置日志组
     */
    String ROUTE_RULE = "route_rule";
    /**
     * 渠道支持支付方式日志组
     */
    String CHANNEL_SUPPORT_PAY_TYPE = "channel_support_pay_type";
    /**
     * 渠道支持银行日志组
     */
    String CHANNEL_SUPPORT_BANK = "channel_support_bank";
    /**
     * 渠道支持银行卡类型组日志组
     */
    String CHANNEL_SUPPORT_BANK_TYPE_GROUP = "channel_support_bank_type_group";
    /**
     * 渠道支持地区组日志组
     */
    String CHANNEL_SUPPORT_BANK_CITY_GROUP = "channel_support_bank_city_group";
    /**
     * 渠道收款商户绑定日志组
     */
    String CHANNEL_MULTI_MERCHANT = "channel_multi_merchant";
    /**
     * 渠道商户信息日志组
     */
    String CHANNEL_MERCHANT_POOL = "channel_merchant_pool";
    /**
     * 渠道结算商户绑定日志组
     */
    String CHANNEL_DF_POOL = "channel_df_pool";
    /**
     * 黑名单日志组
     */
    String BLACK_LIST = "black_list";
    /**
     * 客户交易额度累计日志组
     */
    String CONSUMER_TRANS_SUM = "consumer_trans_sum";
    /**
     * 风险事件记录日志组
     */
    String RISK_LIST = "risk_list";
    /**
     * 渠道扩展参数日志组
     */
    String CHANNEL_EXT_PARAM = "channel_ext_param";
    /**
     * 渠道卡类型对照表日志组
     */
    String CHANNEL_BANK_ACCOUNT_TYPE = "channel_bank_account_type";
    /**
     * 渠道行别对照表日志组
     */
    String CHANNEL_BANK_TYPE = "channel_bank_type";
    /**
     * 渠道证件类型对照表日志组
     */
    String CHANNEL_CERTIFICATE_TYPE = "channel_certificate_type";
    /**
     * 渠道城市代码对照表日志组
     */
    String CHANNEL_CITY = "channel_city";
    /**
     * 渠道行业类别转换对照表日志组
     */
    String CHANNEL_MCC = "channel_mcc";
    /**
     * 渠道返回码日志组
     */
    String CHANNEL_RETURN_CODE = "channel_return_code";
    /**
     * 渠道成本费率配置日志组
     */
    String CHANNEL_FEE = "channel_fee";
    /**
     * 渠道分段计费配置日志组
     */
    String CHANNEL_SEGMENT_FEE = "channel_segment_fee";
    /**
     * 账户调账流水日志组
     */
    String ACCOUNT_ADJUST_BILL = "account_adjust_bill";
    /**
     * 账户冻结解冻明细日志组
     */
    String ACCOUNT_FREEZE_BILL = "account_freeze_bill";
    /**
     * 账户记账事务控制表日志组
     */
    String ACCOUNT_TRANS_BATCH = "account_trans_batch";
    /**
     * 记账明细日志组
     */
    String ACCOUNT_TRANS_DETAIL = "account_trans_detail";
    /**
     * 商户交易业务入网日志组
     */
    String MERCHANT_PRODUCT_TRANS = "merchant_product_trans";
    /**
     * 商户用户银行协议日志组
     */
    String MERCHANT_CONSUMER_PROTOCOL = "merchant_consumer_protocol";
    /**
     * 异步事务控制日志组
     */
    String ASYNC_EVENT_HANDLE = "async_event_handle";
    /**
     * 交易异步处理任务表日志组
     */
    String ASYNC_TRANS_HANDLE = "async_trans_handle";
    /**
     * 退款交易流水日志组
     */
    String REFUND_BILL = "refund_bill";
    /**
     * 退票流水日志组
     */
    String RETURN_BILL = "return_bill";
    /**
     * 交易订单支付明细日志组
     */
    String TRANS_PAY_BILL = "trans_pay_bill";
    /**
     * 账户汇总日报表日志组
     */
    String ACCOUNT_REPORT_DAY = "account_report_day";
    /**
     * 账户余额日终报表日志组
     */
    String ACCOUNT_SUMMARY_DAY = "account_summary_day";
    /**
     * 合作方分润日志组
     */
    String PARTNER_PROFIT = "partner_profit";
    /**
     * 渠道对账明细日志组
     */
    String CHANNEL_CHECK_BILL = "channel_check_bill";
    /**
     * 对账结果差异明细日志组
     */
    String CHANNEL_CHECK_DIFF = "channel_check_diff";
    /**
     * 对账主表日志组
     */
    String CHANNEL_CHECK_RECORD = "channel_check_record";
    /**
     * 下游对账单日志组
     */
    String MERCHANT_CHECK_RECORD = "merchant_check_record";
    /**
     * 商户分润日志组
     */
    String MERCHANT_PROFIT = "merchant_profit";
    /**
     * 商户提现记录表日志组
     */
    String MERCHANT_SETTLE_RECORD = "merchant_settle_record";
    /**
     * 渠道报表日志组
     */
    String REPORT_CHANNEL = "report_channel";
    /**
     * 银行交易汇总表日志组
     */
    String REPORT_TRADE_BANK = "report_trade_bank";
    /**
     * 机构交易汇总表日志组
     */
    String REPORT_TRADE_MERCHANT = "report_trade_merchant";
    /**
     * 客户信息日志组
     */
    String CUST_INFO = "cust_info";
    /**
     * 企业客户信息日志组
     */
    String ENTERPRISE_INFO = "enterprise_info";
    /**
     * 子商户信息日志组
     */
    String SUB_MERCHANT_INFO = "sub_merchant_info";
    /**
     * 合作方信息表日志组
     */
    String PARTNER_INFO = "partner_info";
    /**
     * 合作方关系维护表日志组
     */
    String PARTNER_RELATION_INFO = "partner_relation_info";
    /**
     * 商户操作员表日志组
     */
    String MERCHANT_OPERATOR = "merchant_operator";
    /**
     * mock服务配置日志组
     */
    String MOCK_SERVICE = "mock_service";
}
