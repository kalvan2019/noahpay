package com.noahpay.pay;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.noahpay.pay.bean.ApiModel;
import com.noahpay.pay.util.AppSdk;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author chenliang
 */
public class ApiServer {
    public static final AppSdk APP_SDK = new AppSdk();
    public static final String HTTP = "http:";
    public static final String HTTPS = "https:";
    private static final Log log = Log.get(ApiServer.class);

    /**
     * @param service 接口服务
     * @param appId   接入方ID
     * @param data    请求数据明文
     * @return 结果
     */
    public static String post(String service, String appId, String data) throws Exception {
        return post(service, appId, data, Mode.ECB, SignAlgorithm.SHA1withRSA);
    }

    /**
     * @param service       接口服务如 trans
     * @param appId         接入方ID
     * @param data          请求数据明文
     * @param mode          aes加密填充模式
     * @param signAlgorithm 签名算法
     * @return 结果
     */
    private static String post(String service, String appId, String data, Mode mode, SignAlgorithm signAlgorithm) throws Exception {
        log.info("请求数据明文:{}", data);
        ApiModel sendData = APP_SDK.encryptAndSign(appId, data, mode, signAlgorithm);
        log.info("请求数据密文:{}", JSONUtil.toJsonStr(sendData));
        if (service == null || "".equals(service.trim())) {
            return JSONUtil.toJsonStr(sendData);
        } else {
            if (!service.startsWith(HTTP) && !service.startsWith(HTTPS)) {
                service = APP_SDK.getSdkInfo().getUrl() + "/" + service;
            }
            log.info("接口请求地址:{}", service);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(service);
            //json请求
            StringEntity entity = new StringEntity(JSONUtil.toJsonStr(sendData));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            httpClient.close();
            log.info("响应数据密文:{}", responseStr);
            if (JSONUtil.isJsonObj(responseStr)) {
                ApiModel model = JSONUtil.toBean(responseStr, ApiModel.class);
                APP_SDK.decryptAndVerify(model, mode, signAlgorithm);
                log.info("响应数据明文:{}", JSONUtil.toJsonStr(model));
                String decryptResponseStr = JSONUtil.toJsonStr(model);
                if (!"{}".equals(decryptResponseStr)) {
                    return decryptResponseStr;
                }
            }
            return responseStr;
        }
    }
}
