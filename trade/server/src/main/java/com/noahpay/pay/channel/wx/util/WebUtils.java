package com.noahpay.pay.channel.wx.util;

import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * 网络工具类
 */
@Getter
@Setter
public class WebUtils {

    /**
     * 请求，只请求一次，不做重试
     *
     * @param domain
     * @param urlSuffix
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param useCert          是否使用证书，针对退款、撤销等操作
     * @param mchId            商户号 秘钥
     * @param certFile         证书文件
     * @return
     * @throws Exception
     */
    private static String requestOnce(final String domain, String urlSuffix, String data, int connectTimeoutMs,
                                      int readTimeoutMs, boolean useCert, String mchId, String certFile) throws Exception {
        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            char[] password = mchId.toCharArray();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(certFile), password);
            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);
            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(), null, null, null);
        } else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(), null, null, null);
        }
        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
        String url = domain + urlSuffix;
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);
        StringEntity postEntity = new StringEntity(data, WxPayConstants.CHARSET_UTF8);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", WxPayConstants.USER_AGENT + " " + mchId);
        httpPost.setEntity(postEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, WxPayConstants.CHARSET_UTF8);
    }

    /**
     * 可重试的，非双向认证的请求
     *
     * @param domain
     * @param urlSuffix
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     */
    public static String requestWithoutCert(String domain, String urlSuffix, String data,
                                            int connectTimeoutMs, int readTimeoutMs) throws Exception {
        return requestOnce(domain, urlSuffix, data, connectTimeoutMs, readTimeoutMs, false, null, null);
    }

    /**
     * 可重试的，双向认证的请求
     *
     * @param domain
     * @param urlSuffix
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param mchId
     * @param certFile
     * @return
     */
    public static String requestWithCert(String domain, String urlSuffix, String data,
                                         int connectTimeoutMs, int readTimeoutMs, String mchId, String certFile) throws Exception {
        return requestOnce(domain, urlSuffix, data, connectTimeoutMs, readTimeoutMs, true, mchId, certFile);
    }

    /**
     * get 请求
     *
     * @param domain
     * @param urlSuffix
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    public static String requestGet(String domain, String urlSuffix, String data,
                                    int connectTimeoutMs, int readTimeoutMs) throws Exception {
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(), null, null, null);
        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
        String url = domain + urlSuffix + "?" + data;
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        httpGet.addHeader("User-Agent", WxPayConstants.USER_AGENT);
        httpGet.setHeader("Accept", "text/plain;charset=utf-8");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, WxPayConstants.CHARSET_UTF8);
    }

}
