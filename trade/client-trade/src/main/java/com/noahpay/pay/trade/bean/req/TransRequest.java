package com.noahpay.pay.trade.bean.req;

import com.noahpay.pay.trade.bean.model.Amount;
import com.noahpay.pay.trade.bean.model.PayerInfo;
import com.noahpay.pay.trade.bean.model.SceneInfo;
import com.noahpay.pay.trade.constant.PayTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 交易参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class TransRequest implements java.io.Serializable {
    /**
     * 接入merchantNo
     * 平台分配会关联对应的支付通道参数
     * sp_mchid 机构商户号
     * sub_mchid 子商户号
     * sp_appid 机构APPID
     * sub_appid 子商户APPID
     */
    @NotNull(message = "商户号不能为空")
    private Long merchantNo;
    /**
     * 商户订单号
     * <=32位
     */
    @Size(max = 32, message = "商户订单号")
    @NotBlank(message = "商户订单号不能为空")
    private String orderId;
    /**
     * 商户订单日期
     */
    @NotNull(message = "商户订单日期不能为空")
    private Integer orderDate;
    /**
     * 订单说明
     * <=100
     */
    @Size(max = 128, message = "商品描述")
    @NotBlank(message = "商品描述不能为空")
    private String description;
    /**
     * 通知地址
     */
    @NotBlank(message = "通知地址不能为空")
    private String notifyUrl;
    /**
     * 支付方式
     *
     * @see PayTypeEnum
     */
    @NotNull(message = "支付方式不能为空")
    private String payType;
    /**
     * 附加数据
     * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    private String attach;
    /**
     * 商品ID
     * payType=WX_NATIVE时，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
     */
    private String productId;
    /**
     * 交易起始时间
     * 订单生成时间，格式为yyyy-MM-dd HH:mm:ss
     */
    private Date timeStart;
    /**
     * 交易结束时间
     * 订单失效时间，格式为yyyy-MM-dd HH:mm:ss
     */
    private Date timeExpire;
    /**
     * 订单金额
     */
    @Valid
    private Amount amount;
    /**
     * 付款方信息
     */
    @Valid
    private PayerInfo payerInfo;
    /**
     * 场景信息
     */
    @Valid
    private SceneInfo sceneInfo;
}
