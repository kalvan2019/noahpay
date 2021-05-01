package com.noahpay.pay.db.algorithm;

/**
 * @author chenliang
 */
public interface ShardingConfig {
    String DEFAULT_PART_LIBRARY = "1";
    String DEFAULT_PART_TABLES = "00";
    /**
     * cust分表数量
     */
    int SPLIT_TABLE = 1;
}
