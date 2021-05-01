package com.noahpay.pay.db.algorithm;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * merchantNo 分表算法
 *
 * @author chenliang
 */
public class MerchantNoShardingAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        List<String> tables = new ArrayList<>();
        String logicTableName = complexKeysShardingValue.getLogicTableName();
        // 单分表查找(优先使用transId)
        Map<String, Collection<Integer>> map = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        Collection merchantNo = map.get("merchant_no");
        if (merchantNo != null && !merchantNo.isEmpty()) {
            //根据交易流水号精确分表
            tables.add(logicTableName + "_" + getSubMerchantPartTable((Long) merchantNo.iterator().next()));
            return tables;
        }
        return tables;
    }

    /**
     * 根据商户编号获取分表
     *
     * @param merchantNo 商户编号
     * @return 2位分表数
     */
    public static String getSubMerchantPartTable(Long merchantNo) {
        int code = merchantNo.hashCode() & Integer.MAX_VALUE;
        int partTable = code % ShardingConfig.SPLIT_TABLE;
        String tableSeq = String.valueOf(partTable);
        if (tableSeq.length() == 1) {
            tableSeq = "0" + tableSeq;
        }
        return tableSeq;
    }
}
