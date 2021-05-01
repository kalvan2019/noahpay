package com.noahpay.pay.job.trade.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenliang
 */
public final class AccountThreadMonitor {
    public static final Object OBJ = new Object();
    /**
     * 同一账户记账并发数设置,全局共用
     */
    public static final int CONCURRENT = 1;
    /**
     * <accountNo,并发数>
     */
    private static final Map<Long, Integer> RUN_TASK = new ConcurrentHashMap<>();
    /**
     * 当前总账户处理数
     */
    private static int runCount = 0;

    private AccountThreadMonitor() {
    }

    public static int getRunCount() {
        return runCount;
    }

    public static Integer getConcurrent(Long accountNo) {
        return RUN_TASK.get(accountNo) == null ? 0 : RUN_TASK.get(accountNo);
    }

    public static void addWorker(Long accountNo) {
        synchronized (OBJ) {
            runCount++;
            if (accountNo == null) {
                return;
            }
            Integer sum = RUN_TASK.get(accountNo);
            if (sum == null || sum < 0) {
                sum = 0;
            }
            RUN_TASK.put(accountNo, ++sum);
        }
    }

    public static void minusWorker(Long accountNo) {
        try {
            synchronized (OBJ) {
                runCount--;
                if (accountNo == null) {
                    return;
                }
                Integer sum = RUN_TASK.get(accountNo);
                if (sum == null) {
                    return;
                }
                if (--sum <= 0) {
                    RUN_TASK.remove(accountNo);
                } else {
                    RUN_TASK.put(accountNo, sum);
                }
            }
        } catch (Throwable e) {
            clear();
        }
    }

    public static void clear() {
        RUN_TASK.clear();
    }
}
