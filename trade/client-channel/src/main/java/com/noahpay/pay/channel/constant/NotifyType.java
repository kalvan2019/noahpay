package com.noahpay.pay.channel.constant;

/**
 * 异常交易通知类型
 *
 * <li>PAGE 页面通知,即前台通知
 *
 * <li>MSG 后台通知,即报文通知
 *
 * @author chenliang
 */
public interface NotifyType {
    /**
     * 页面通知,即前台通知
     */
    String PAGE = "page";
    /**
     * 后台通知,即报文通知
     */
    String MSG = "msg";
}
