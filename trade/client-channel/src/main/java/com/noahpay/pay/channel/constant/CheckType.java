package com.noahpay.pay.channel.constant;

/**
 * 对账类型
 *
 * @author chenliang
 */
public interface CheckType {
    /**
     * 2:联机对账,忽略本地缓存
     */
    String ONLINE = "2";
    /**
     * 3:使用ftp文件对账,忽略本地缓存
     */
    String FTP = "3";
}
