package com.noahpay.pay.enums.mq.msg;

import lombok.Getter;
import lombok.Setter;

/***
 * @author chenliang
 */
@Getter
@Setter
public class EmailNotify {
    /**
     * 商户名称
     */
    String merchantName;

    /**
     * 接收人邮箱
     */
    String recvEmail;

    /**
     * 标题
     */
    String titleName;

    /**
     * 正文
     */
    String content;

    /**
     * 邮件类型
     */
    Integer emailType;

    /**
     * 模板文件路径
     */
    String filePath;
}
