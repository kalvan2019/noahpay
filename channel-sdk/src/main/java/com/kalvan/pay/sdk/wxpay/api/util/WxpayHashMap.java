package com.kalvan.pay.sdk.wxpay.api.util;

import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 纯字符串字典结构
 */
@Getter
@Setter
public class WxpayHashMap extends HashMap<String, String> {

    private static final long serialVersionUID = 1277791390393392630L;

    public WxpayHashMap() {
        super();
    }

    public WxpayHashMap(Map<? extends String, ? extends String> m) {
        super(m);
    }

    public String put(String key, Object value) {
        String strValue;

        if (value == null) {
            strValue = null;
        } else if (value instanceof String) {
            strValue = (String) value;
        } else if (value instanceof Integer) {
            strValue = ((Integer) value).toString();
        } else if (value instanceof Long) {
            strValue = ((Long) value).toString();
        } else if (value instanceof Float) {
            strValue = value.toString();
        } else if (value instanceof Double) {
            strValue = value.toString();
        } else if (value instanceof Boolean) {
            strValue = ((Boolean) value).toString();
        } else if (value instanceof Date) {
            DateFormat format = new SimpleDateFormat(WxpayConstants.DATE_TIME_FORMAT);
            format.setTimeZone(TimeZone.getTimeZone(WxpayConstants.DATE_TIMEZONE));
            strValue = format.format((Date) value);
        } else {
            strValue = value.toString();
        }
        return this.put(key, strValue);
    }

    @Override
    public String put(String key, String value) {
        if (StringUtils.areNotEmpty(key, value)) {
            return super.put(key, value);
        } else {
            return null;
        }
    }

}
