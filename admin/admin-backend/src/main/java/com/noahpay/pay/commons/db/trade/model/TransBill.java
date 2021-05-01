package com.noahpay.pay.commons.db.trade.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.NumberConvert;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 交易订单实体
 * 表名 trans_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "trans_bill")
public class TransBill implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @ExcelProperty(value = "自增id",converter = LongStringConverter.class)
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 交易流水号
     */
    @ShardingUk
    @ExcelProperty(value = "交易流水号")
    @Column(name = "trans_id")
    private String transId;

    /**
     * 交易日期
     */
    @ExcelProperty(value = "交易日期",converter = IntegerStringConverter.class)
    @Column(name = "trans_date")
    private Integer transDate;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_TYPE)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 订单号
     */
    @ExcelProperty(value = "订单号")
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单日期
     */
    @ExcelProperty(value = "订单日期",converter = IntegerStringConverter.class)
    @Column(name = "order_date")
    private Integer orderDate;

    /**
     * 订单说明
     */
    @ExcelProperty(value = "订单说明")
    @Column(name = "order_note")
    private String orderNote;

    /**
     * 订单金额
     */
    @ExcelProperty(value = "订单金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "order_amount")
    private Long orderAmount;

    /**
     * 域名
     */
    @ExcelProperty(value = "域名")
    @Column(name = "order_domain")
    private String orderDomain;

    /**
     * 商户端IP
     */
    @ExcelProperty(value = "商户端IP")
    @Column(name = "order_ip")
    private String orderIp;

    /**
     * 设备信息
     */
    @ExcelProperty(value = "设备信息")
    @Column(name = "order_device_info")
    private String orderDeviceInfo;

    /**
     * 行业类别
     */
    @ExcelProperty(value = "行业类别",converter = IntegerStringConverter.class)
    @Column(name = "order_mcc")
    private Integer orderMcc;

    /**
     * 城市代码
     */
    @ExcelProperty(value = "城市代码",converter = IntegerStringConverter.class)
    @Column(name = "order_city")
    private Integer orderCity;

    /**
     * 失效时间
     */
    @ExcelProperty(value = "失效时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "order_expiry_time")
    private Date orderExpiryTime;

    /**
     * 页面通知地址
     */
    @ExcelProperty(value = "页面通知地址")
    @Column(name = "notify_pg_url")
    private String notifyPgUrl;

    /**
     * 后台通知地址
     */
    @ExcelProperty(value = "后台通知地址")
    @Column(name = "notify_bg_url")
    private String notifyBgUrl;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 商户名
     */
    @ExcelProperty(value = "商户名")
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 商户客户号
     */
    @ExcelProperty(value = "商户客户号",converter = LongStringConverter.class)
    @Column(name = "merchant_cust_no")
    private Long merchantCustNo;

    /**
     * 商户账户号
     */
    @ExcelProperty(value = "商户账户号",converter = LongStringConverter.class)
    @Column(name = "merchant_account_no")
    private Long merchantAccountNo;

    /**
     * 商户手续费
     */
    @ExcelProperty(value = "商户手续费",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "merchant_fee")
    private Long merchantFee;

    /**
     * 子商户号
     */
    @ExcelProperty(value = "子商户号",converter = LongStringConverter.class)
    @Column(name = "sub_merchant_no")
    private Long subMerchantNo;

    /**
     * 子商户客户号
     */
    @ExcelProperty(value = "子商户客户号",converter = LongStringConverter.class)
    @Column(name = "sub_merchant_cust_no")
    private Long subMerchantCustNo;

    /**
     * 子商户账户号
     */
    @ExcelProperty(value = "子商户账户号",converter = LongStringConverter.class)
    @Column(name = "sub_merchant_account_no")
    private Long subMerchantAccountNo;

    /**
     * 子商户手续费
     */
    @ExcelProperty(value = "子商户手续费",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "sub_merchant_fee")
    private Long subMerchantFee;

    /**
     * 客户手续费
     */
    @ExcelProperty(value = "客户手续费",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "consumer_fee")
    private Long consumerFee;

    /**
     * 结算金额
     */
    @ExcelProperty(value = "结算金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "settlement_amount")
    private Long settlementAmount;

    /**
     * 记账流水号
     */
    @ExcelProperty(value = "记账流水号")
    @Column(name = "account_trans_id")
    private String accountTransId;

    /**
     * 账务记账时间
     */
    @ExcelProperty(value = "账务记账时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "account_trans_time")
    private Date accountTransTime;

    /**
     * 会计日期
     */
    @ExcelProperty(value = "会计日期",converter = IntegerStringConverter.class)
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 冲正记账流水号
     */
    @ExcelProperty(value = "冲正记账流水号")
    @Column(name = "undo_account_trans_id")
    private String undoAccountTransId;

    /**
     * 冲正记账时间
     */
    @ExcelProperty(value = "冲正记账时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "undo_account_trans_time")
    private Date undoAccountTransTime;

    /**
     * 冲正会计日期
     */
    @ExcelProperty(value = "冲正会计日期",converter = IntegerStringConverter.class)
    @Column(name = "undo_account_date")
    private Integer undoAccountDate;

    /**
     * 支付模式
     */
    @ExcelProperty(value = "支付模式",converter = IntegerStringConverter.class)
    @Dict(DictType.PAY_MODEL)
    @Column(name = "pay_model")
    private Integer payModel;

    /**
     * 支付类型
     */
    @ExcelProperty(value = "支付类型",converter = IntegerStringConverter.class)
    @Dict(DictType.PAY_TYPE)
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 支付流水号
     */
    @ExcelProperty(value = "支付流水号",converter = LongStringConverter.class)
    @Column(name = "pay_id")
    private Long payId;

    /**
     * 路由扩展数据
     */
    @ExcelProperty(value = "路由扩展数据")
    @Column(name = "pay_route_info")
    private String payRouteInfo;

    /**
     * 支付网关返回码
     */
    @ExcelProperty(value = "支付网关返回码")
    @Column(name = "pay_result_code")
    private String payResultCode;

    /**
     * 支付网关备注
     */
    @ExcelProperty(value = "支付网关备注")
    @Column(name = "pay_result_note")
    private String payResultNote;

    /**
     * 交易状态
     */
    @ExcelProperty(value = "交易状态",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 通知状态
     */
    @ExcelProperty(value = "通知状态",converter = IntegerStringConverter.class)
    @Dict(DictType.NOTIFY_STATE)
    @Column(name = "notify_state")
    private Integer notifyState;

    /**
     * 对账状态
     */
    @ExcelProperty(value = "对账状态",converter = IntegerStringConverter.class)
    @Dict(DictType.CHECK_STATE)
    @Column(name = "check_state")
    private Integer checkState;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

}
