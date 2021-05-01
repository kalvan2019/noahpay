package com.noahpay.pay.commons.constant;

/**
 * 审核状态
 *
 * @author chenliang
 */
public enum AuditState {
    /**
     * 审核状态
     */
    PASS(0, "审核通过"),
    REFUSE(1, "审核拒绝"),
    WAIT(2, "待审核"),
    ;

    public int code;
    public String desc;

    AuditState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
