package com.noahpay.pay.trade.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 交易流水表分库按merchant_no号第1位来分
 * 分表按商户订单日期order_date来分,暂时不应用分表规则
 * 如果要分时要迁移数据后再应用分表规则
 *
 * @author chenliang
 */
@Slf4j
public class TransIdUtils {
    /**
     * 每秒序号(1000个)平分给几个节点
     */
    static int count = 1000;
    /**
     * 如有2个节点,服务应用启动时向zk获取一个序号开始为1，后面新节点启动者再去获取序号+1
     */
    static int nodes = 1;
    /**
     * 当前节点
     */
    static int currentNode = 1;
    /**
     * 每个节点多少个号
     */
    static int split = 1000;
    /**
     * 当前节点开始号码
     */
    static int beginIndex = 0;
    /**
     * 当前节点结束号码
     */
    static int endIndex = 1000;
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * 获取序号
     */
    static String currentHhmmss = "";

    /**
     * 初始化节点信息
     *
     * @param nodesParam       总节点
     * @param currentNodeParam 1开始
     */
    public static void initNode(int nodesParam, int currentNodeParam) {
        log.info("初始化trans_id生成节点信息,总节点{},当前节点{}", nodesParam, currentNodeParam);
        nodes = nodesParam;
        currentNode = currentNodeParam;
        split = count / nodes;
        beginIndex = split * (currentNode - 1);
        endIndex = split * currentNode - 1;
        log.info("split{},beginIndex{},endIndex{}", count, beginIndex, endIndex);
        atomicInteger.set(beginIndex);
    }

    /**
     * 生成交易流水号trans_id(20位)
     * 1(分库) =member_no第一位
     * 8(按日分表,暂时不使用使用时迁移数据)=商户订单日期
     * 6(时分秒)=HHmmss
     * 2(交易类型)=trans_type
     * 3(序号)=通过redis批量拿号999/机器节点数
     *
     * @param merchantNo merchantNo
     * @param orderDate  orderDate
     * @param transType  transType
     * @return String
     */
    public static String genTransId(long merchantNo, int orderDate, int transType) {
        String dbNumber = String.valueOf(merchantNo).substring(0, 1);
        StringBuffer buffer = new StringBuffer();
        buffer.append(dbNumber)
                .append(orderDate)
                .append(getSeq(transType))
        ;
        log.info("生成交易流水号:{},[{}][{}]", buffer.toString(), dbNumber, orderDate);
        return buffer.toString();
    }

    public static int getOrderDate(String transIdOrSendSn) {
        return Integer.parseInt(transIdOrSendSn.substring(1, 9));
    }

    /**
     * 每秒为单位放号  HHmmss+trans_type+seq
     */
    private static synchronized String getSeq(int transType) {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
        String hhmmss = dateFormat.format(date);
        //获取当前值,自动+1
        //生产表现有并发拿到相同号的问题，加个锁
        int seq = atomicInteger.getAndIncrement();
        if (StringUtils.isBlank(currentHhmmss) || hhmmss.equals(currentHhmmss)) {
            //更新时间
            if (seq <= endIndex) {
                log.debug("1秒内并发放号:{}", seq);
                currentHhmmss = hhmmss;
                //1秒内放号 +1
                return hhmmss +
                        transType +
                        StringUtils.leftPad(seq + "", 3, "0");
            } else {
                log.info("1秒内号已放完:{}", seq);
                //如果一秒内号放完了则休眠
                try {
                    Thread.sleep(RandomUtils.nextInt(10, 1000));
                } catch (InterruptedException e) {
                }
                return getSeq(transType);
            }
        } else {
            log.debug("放号复位完成");
            //超过时间窗口更新
            atomicInteger.set(beginIndex);
            currentHhmmss = hhmmss;
            return getSeq(transType);
        }
    }
}
