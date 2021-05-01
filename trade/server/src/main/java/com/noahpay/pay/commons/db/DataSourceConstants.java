package com.noahpay.pay.commons.db;


/**
 * DataSource配置常量
 *
 * @author kalvan.tools:kalvan
 */
public class DataSourceConstants {
    public static final String TRADE_MAPPER_PACKAGE = "com.noahpay.pay.commons.db.trade.mapper";
    public static final String TRADE_MAPPER_PATH = "classpath:/db/trade/mapper/*.xml";
    public static final String TRADE_SHARDING_YML = "db-sharding-trade.yaml";
    public static final String TRADE_DATA_SOURCE = "tradeDataSource";
    public static final String TRADE_TRANSACTION_MANAGER = "tradeTransactionManager";
    public static final String TRADE_SESSION_FACTORY = "tradeSessionFactory";

    public static final String CUST_MAPPER_PACKAGE = "com.noahpay.pay.commons.db.cust.mapper";
    public static final String CUST_MAPPER_PATH = "classpath:/db/cust/mapper/*.xml";
    public static final String CUST_SHARDING_YML = "db-sharding-cust.yaml";
    public static final String CUST_DATA_SOURCE = "custDataSource";
    public static final String CUST_TRANSACTION_MANAGER = "custTransactionManager";
    public static final String CUST_SESSION_FACTORY = "custSessionFactory";

    public static final String CHANNEL_MAPPER_PACKAGE = "com.noahpay.pay.commons.db.channel.mapper";
    public static final String CHANNEL_MAPPER_PATH = "classpath:/db/channel/mapper/*.xml";
    public static final String CHANNEL_SHARDING_YML = "db-sharding-channel.yaml";
    public static final String CHANNEL_DATA_SOURCE = "channelDataSource";
    public static final String CHANNEL_TRANSACTION_MANAGER = "channelTransactionManager";
    public static final String CHANNEL_SESSION_FACTORY = "channelSessionFactory";

    public static final String RISK_MAPPER_PACKAGE = "com.noahpay.pay.commons.db.risk.mapper";
    public static final String RISK_MAPPER_PATH = "classpath:/db/risk/mapper/*.xml";
    public static final String RISK_SHARDING_YML = "db-sharding-risk.yaml";
    public static final String RISK_DATA_SOURCE = "riskDataSource";
    public static final String RISK_TRANSACTION_MANAGER = "riskTransactionManager";
    public static final String RISK_SESSION_FACTORY = "riskSessionFactory";
}