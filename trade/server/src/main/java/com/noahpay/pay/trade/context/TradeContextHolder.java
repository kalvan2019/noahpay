package com.noahpay.pay.trade.context;

import com.kalvan.client.context.RequestContext;
import com.kalvan.client.context.RequestContextHolder;

/**
 * @author chenliang
 */
public class TradeContextHolder {
    public static final String TRADE_CONTEXT = "TRADE_CONTEXT";

    private TradeContextHolder() {
    }

    /**
     * 获取交易上下文
     *
     * @return TradeContext
     */
    public static TradeContext getTradeContext() {
        RequestContext requestContext = RequestContextHolder.getContext();
        TradeContext tradeContext = (TradeContext) requestContext.get(TRADE_CONTEXT);
        if (tradeContext == null) {
            tradeContext = new TradeContext();
            requestContext.put(TRADE_CONTEXT, tradeContext);
        }
        return tradeContext;
    }
}
