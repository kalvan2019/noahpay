package com.noahpay.pay.channel.context;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * 通道数据context
 *
 * @author chenliang
 */
@Getter
@Setter
public class ChannelContext implements java.io.Serializable {
    /**
     * 通配符
     */
    public static final String WILD_CARDS = "*";
    private static final long serialVersionUID = 1L;
    /**
     * 当前通道号
     */
    protected Integer channelNo;
    /**
     * 通道默认的商户号
     */
    private String channelMerchantNo;
    /**
     * 通道实现类
     */
    private String channelClass;
    /**
     * 商户池转换标识-0转/1不转,转换关系表merchant_pool_flag
     */
    private Integer merchantPoolFlag;
    /**
     * 多商户转换标识-0转/1不转,转换关系表multi_merchant_flag
     */
    private Integer multiMerchantFlag;
    /**
     * 是否转换发往银行流水号-0转/1不转,如果不需要转换则直接以交易传递过来的为准
     */
    private Integer sendSnConvert;
    /**
     * 是否转换城市代码-0转/1不转,转换关系表city_code_convert
     */
    private Integer cityCodeConvert;
    /**
     * 是否是行别换号-0转/1不转,转换关系表channel_bank_type
     */
    private Integer bankTypeConvert;
    /**
     * 是否需要转换账户类型-0转/1不转
     */
    private Integer bankAccountTypeConvert;
    /**
     * 是否转换证件-0转/1不转,转换关系表certificate_type_convert
     */
    private Integer certificateTypeConvert;
    /**
     * 是否转换行业类目-0转/1不转，转换关系表mcc_convert
     */
    private Integer mccConvert;
    /**
     * 支持对账 0-支持对账;1-不支持
     */
    private Integer checkSupport;
    /**
     * 对账时间(HHmm)
     */
    private String checkTime;
    /**
     * 对账凭证,1我方流水2上游流水
     */
    private Integer checkField;
    /**
     * 结算周期,0表示D0
     */
    private String settlementTime;
    /**
     * 连接超时时间，以秒为单位
     */
    private Integer connectionTimeout;
    /**
     * 通道最大并发数,0表示不控制
     */
    private Integer connectionMaxNum;
    /**
     * 读超时时间，以秒为单位
     */
    private Integer readTimeout;
    /**
     * 是否通道支持发送短信
     */
    private Integer smsSupport;
    /**
     * 状态,0启用1禁用
     */
    private Integer state;
    /**
     * 所有通道商户的参数<br>
     * key:merchant<br>
     * value: channelExtParam 表数据map
     * key为*标识通配，如 Map<"*", Map<"bgUrl", ""https:callback.com/***">>
     */
    private Map<String, Map<String, String>> channelParamMap;

    public String getExtParamValue(String channelMerchantNo, String paramKey) {
        if (channelParamMap != null) {
            Map<String, String> merMap = channelParamMap.get(channelMerchantNo);
            Map<String, String> globMap = channelParamMap.get(WILD_CARDS);
            if (merMap != null) {
                if (globMap != null) {
                    Iterator<String> iterator = globMap.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        if (StringUtils.isBlank(merMap.get(key))) {
                            merMap.put(key, globMap.get(key));
                        }
                    }
                }
                return channelParamMap.get(channelMerchantNo).get(paramKey);
            } else if (globMap != null) {
                return globMap.get(paramKey);
            }
        }
        return null;
    }
}
