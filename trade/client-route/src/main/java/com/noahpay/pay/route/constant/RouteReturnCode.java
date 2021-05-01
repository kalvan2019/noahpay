package com.noahpay.pay.route.constant;

import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.model.ReturnCode;

/**
 * 系统返回码定义
 *
 * @author chenliang
 */
public interface RouteReturnCode extends CommonReturnCode {
    ReturnCode CHANNEL_CLOSE = new ReturnCode("CHANNEL_CLOSE", "通道未生效{0}");
    ReturnCode CODE_6412 = new ReturnCode("6412", "无可用路由{0}");
}
