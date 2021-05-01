package com.noahpay.pay.enums.mq.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Getter
@Setter
public class TransNotifyResult {
    /**
     * 交易流水号
     */
    String transId;
    /**
     * 交易类型
     */
    Integer transType;

    /**
     * 通知状态
     * 0通知成功
     * 1待发送
     * 2通知失败
     * 3非法通知地址
     * 4黑名单限制
     */
    Integer notifyState;

}
