package com.noahpay.pay.channel.bean.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用通道批量交易明细对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class CheckDetailInfo implements java.io.Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * 交易资金类型,收和付
     */
    private Integer type;
    /**
     * 通道商户号
     */
    private String channelMerchantNo;
    /**
     * 发往银行流水
     */
    private String channelSendSn;
    /**
     * 银行返回流水
     */
    private String channelRecvSn;
    /**
     * 明细交易状态
     */
    private Integer transState;
    /**
     * 交易时间戳
     */
    private String transTime;
    /**
     * 交易金额
     */
    private Long amount;
    /**
     * 交易手续费金额
     */
    private Long fee;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    /**
     * 账户名
     */
    private String bankAccountName;
    /**
     * 原发往银行流水(反向交易时，有些通道只返回原订单信息)
     */
    private String orgChannelSendSn;
    /**
     * 原交易金额
     */
    private Long orgAmount;
    /**
     * 原交易手续费金额
     */
    private Long orgFee;
//
//    /**
//     * 通道银行账号类型
//     */
//    private String channelBankAccountType;
//    /**
//     * 退票日期
//     */
//    private String returnTicketDate;
//    /**
//     * 返回码
//     */
//    private String resultCode;
//    /**
//     * 返回说明
//     */
//    private String resultNote;
//    /**
//     * 扩展字段,特殊通道对账时可将数据传到此处
//     */
//    private String ext;
//    /**
//     * 网络服务费（银联通道）
//     */
//    private Long networkFee;
//    /**
//     * 品牌服务费（银联通道）
//     */
//    private Long brandFee;
}
