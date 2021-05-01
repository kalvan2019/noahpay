package com.noahpay.pay.db.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * account分库分表
 *
 * @author chenliang
 */
public class AccountTransDayShardingAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        List<String> tables = new ArrayList<>();
        String logicTableName = complexKeysShardingValue.getLogicTableName();
        // 单分表查找
        Map<String, Collection<Integer>> map = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        //优先用accountDate识别分表
        Collection orderDate = map.get("account_date");
        if (orderDate != null && !orderDate.isEmpty()) {
            //根据订单日期精确分表
            tables.add(logicTableName + "_" + orderDate.iterator().next());
            return tables;
        }
        //范围查询
        Map<String, Range<Integer>> rangeMap = complexKeysShardingValue.getColumnNameAndRangeValuesMap();
        Range<Integer> orderDateRange = rangeMap.get("account_date");
        if (orderDateRange != null) {
            String startDay = String.valueOf(orderDateRange.lowerEndpoint());
            String endDay = String.valueOf(orderDateRange.upperEndpoint());
            DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startTime, endTime;
            try {
                startTime = sdf.parse(startDay);
                endTime = sdf.parse(endDay);
                Calendar cal = Calendar.getInstance();
                int size = 0;
                while (startTime.getTime() <= endTime.getTime()) {
                    if (size > 30) {
                        throw new RuntimeException("分表查询不允许超过31天");
                    }
                    tables.add(logicTableName + "_" + sdf.format(startTime));
                    // 设置起时间
                    cal.setTime(startTime);
                    cal.add(Calendar.DAY_OF_WEEK, 1);
                    startTime = cal.getTime();
                    size++;
                }
            } catch (ParseException e) {
                throw new RuntimeException("分表日期解析出错");
            }
            // 反转排序，由于sharing-jdbc 跨分表查询大结果集时时间反序会按第一个分表的反序开始显示数据，所以要把较后的分表先去执行
            Collections.reverse(tables);
            return tables;
        }
        //没有传accountDate则使用transId识别分表
        Collection transId = map.get("account_trans_id");
        if (transId != null && !transId.isEmpty()) {
            //根据交易流水号精确分表
            tables.add(logicTableName + "_" + transId.iterator().next().toString().substring(1, 9));
            return tables;
        }
        throw new RuntimeException("未匹配到数据库");
    }
}