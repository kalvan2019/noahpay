package com.noahpay.pay.trade.iface.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 这是容错类,他要求我们要是实现一个FallbackFactory<要为哪个接口产生容错类>
 *
 * @author chenliang
 */
@Component
@Slf4j
public class TradeFallbackFactory implements FallbackFactory {
    TradeFallback fallback = new TradeFallback();

    @Override
    public Object create(Throwable t) {
        log.error("容错error", t);
        return fallback;
    }
}