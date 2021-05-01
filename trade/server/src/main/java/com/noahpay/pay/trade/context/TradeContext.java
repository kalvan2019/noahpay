package com.noahpay.pay.trade.context;

import com.noahpay.pay.cust.bean.res.MerchantCheckTransResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 交易过程使用的context ,注意方法处理结束后需要清理,避免线程问题
 *
 * @author chenliang
 */
@Getter
@Setter
public class TradeContext {
    /**
     * 子商户交易模式
     */
    private boolean subMerchantTrans;
    /**
     * 控制是否需要通知
     */
    private boolean notifyFlag = true;
    /**
     * 商户交易入网信息
     */
    private MerchantCheckTransResponse checkTransResponse;
}
