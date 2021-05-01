package com.noahpay.pay.enums.cache;

/***
 * @author chenliang
 */
public enum SolrCacheEnum {

    /**
     * solr缓存用于管理系统下拉查询
     */
    SOLR_MERCHANT_CORE("merchant_core", "商户信息"),
    ;

    public String core;
    public String desc;

    SolrCacheEnum(String core, String desc) {
        this.core = core;
        this.desc = desc;
    }
}
