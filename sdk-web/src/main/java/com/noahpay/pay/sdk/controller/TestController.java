package com.noahpay.pay.sdk.controller;

import cn.hutool.json.JSONUtil;
import com.noahpay.pay.sdk.bean.req.TestRequest;
import com.noahpay.pay.ApiServer;
import com.noahpay.pay.bean.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 * 测试控制类
 *
 * @author chenliang
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
    /**
     * 解析返回的参数
     */
    public static Map<String, String> parseNotifyMsg(Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<>(10);
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * API测试接口
     * 自动签名并发送交易
     *
     * @param service 真实请求服务
     * @param request 请求参数
     * @return 处理结果
     * @throws Exception Exception
     */
    @PostMapping("send/api/{service}")
    public Object test(@PathVariable(value = "service", required = false) String service,
                       @RequestBody TestRequest request) throws Exception {
        if (!StringUtils.isEmpty(request.getService())) {
            service = request.getService();
        }
        //使用sdk加密和签名再转发请求
        String result = ApiServer.post(service, request.getAppId(), JSONUtil.toJsonStr(request.getData()));
        if (JSONUtil.isJsonObj(result)) {
            return JSONUtil.parseObj(result).get("data");
        }
        return result;
    }

    /**
     * 后台回调接口
     * 模拟下游接收异步通知并返回成功标识
     *
     * @param model 通知参数
     * @return 接收成功返回标识避免重发 0000
     */
    @RequestMapping(value = "notify/bg", method = {RequestMethod.POST, RequestMethod.GET})
    public String notifyBg(@RequestBody ApiModel model) {
        log.info("上游异步回调通知参数为：{}", JSONUtil.toJsonStr(model));
        model = ApiServer.APP_SDK.decryptAndVerify(model);
        log.info("解密数据：{}", JSONUtil.toJsonStr(model));
        log.info("异步通知接收成功返回成功握手报文0000");
        return "0000";
    }

    /**
     * 页面回调接口
     * 模拟下游接收异步通知并返回成功标识
     */
    @RequestMapping(value = "notify/pg", method = {RequestMethod.POST, RequestMethod.GET})
    public void notifyPg(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> result = request.getParameterMap();
            Map<String, String> resultMap = parseNotifyMsg(result);
            log.info("上游异步页面回调通知参数为：{}", resultMap);
            response.sendRedirect("https://kalvan.store");
        } catch (Throwable e) {
            log.error("接收异步页面回调出现异常：", e);
        }
    }
}
