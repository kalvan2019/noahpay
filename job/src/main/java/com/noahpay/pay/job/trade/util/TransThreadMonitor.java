package com.noahpay.pay.job.trade.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenliang
 */
public final class TransThreadMonitor {
    /**
     * 执行中的任务唯一流水
     */
    private static final Map<String, Integer> RUN_TASK = new ConcurrentHashMap<>();

    private TransThreadMonitor() {
    }

    public static boolean isRun(String transId) {
        return RUN_TASK.get(transId) != null;
    }

    public static void addWorker(String transId) {
        if (transId == null) {
            return;
        }
        RUN_TASK.put(transId, 0);
    }

    public static void minusWorker(String transId) {
        try {
            RUN_TASK.remove(transId);
        } catch (Throwable e) {
            clear();
        }
    }

    public static void clear() {
        RUN_TASK.clear();
    }
}
