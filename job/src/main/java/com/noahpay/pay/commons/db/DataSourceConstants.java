package com.noahpay.pay.commons.db;

/**
 * DataSource配置常量
 *
 * @author kalvan.tools
 */
public class DataSourceConstants {
    public static final String TRADE_MAPPER_PACKAGE = "com.kalvan.commons.db.trade.mapper";
    public static final String TRADE_MAPPER_PATH = "classpath:/db/trade/mapper/*.xml";
    public static final String TRADE_SHARDING_YML = "db-sharding-trade.yaml";
    public static final String TRADE_DATA_SOURCE = "tradeDataSource";
    public static final String TRADE_TRANSACTION_MANAGER = "tradeTransactionManager";
    public static final String TRADE_SESSION_FACTORY = "tradeSessionFactory";
}