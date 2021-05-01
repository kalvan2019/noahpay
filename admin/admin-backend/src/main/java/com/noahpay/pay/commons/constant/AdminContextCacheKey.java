package com.noahpay.pay.commons.constant;

import com.kalvan.client.constant.CommonContextCacheKey;

/**
 * 存放在ApiContext 中的缓存
 *
 * @author kalvan
 */
public interface AdminContextCacheKey extends CommonContextCacheKey {
    /**
     * appIds
     */
    String APP_IDS = "appIds";
    /**
     * authorizes
     */
    String AUTHORIZES = "authorizes";
    /**
     * services
     */
    String SERVICES = "services";
}
