package com.noahpay.pay.commons.constant;

import com.kalvan.admin.dict.SystemDictType;

/**
 * 数据字典类型定义(扩展)
 *
 * @author kalvan
 */
public interface DictType extends SystemDictType {
    /**
     * 银行帐户类型字典类型
     */
    String BANK_ACCOUNT_TYPE = "bank_account_type";
    /**
     * 行别字典类型
     */
    String BANK_TYPE = "bank_type";
    /**
     * 账户类型字典类型
     */
    String ACCOUNT_TYPE = "account_type";
    /**
     * 交易类型字典类型
     */
    String TRANS_TYPE = "trans_type";
    /**
     * 交易状态字典类型
     */
    String TRANS_STATE = "trans_state";
    /**
     * 支付模式字典类型
     */
    String PAY_MODEL = "pay_model";
    /**
     * 支付类型字典类型
     */
    String PAY_TYPE = "pay_type";
    /**
     * 计费方式字典类型
     */
    String FEE_MODE = "fee_mode";
    /**
     * 计费对象字典类型
     */
    String FEE_OBJECT = "fee_object";
    /**
     * 计费方法字典类型
     */
    String FEE_TYPE = "fee_type";
    /**
     * 渠道编号字典类型
     */
    String CHANNEL_NO = "channel_no";
    /**
     * 开户行所在地字典类型
     */
    String BANK_CITY = "bank_city";
    /**
     * 黒名单类型字典类型
     */
    String BLACK_TYPE = "black_type";
    /**
     * 风控等级字典类型
     */
    String RISK_LEVEL = "risk_level";
    /**
     * 证件类型字典类型
     */
    String CERTIFICATE_TYPE = "certificate_type";
    /**
     * 行业类别字典类型
     */
    String MCC = "mcc";
    /**
     * 凭证类型字典类型
     */
    String VOUCHER_TYPE = "voucher_type";
    /**
     * 事件类型字典类型
     */
    String EVENT_TYPE = "event_type";
    /**
     * 执行策略字典类型
     */
    String STRATEGY_TYPE = "strategy_type";
    /**
     * 状态字典类型
     */
    String EVENT_STATE = "event_state";
    /**
     * 通知状态字典类型
     */
    String NOTIFY_STATE = "notify_state";
    /**
     * 对账状态字典类型
     */
    String CHECK_STATE = "check_state";
    /**
     * 行业字典类型
     */
    String INDUSTRY = "industry";
}
