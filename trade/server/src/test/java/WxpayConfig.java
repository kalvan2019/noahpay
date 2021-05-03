import lombok.Getter;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信公众号/商户号配置信息接口
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "channel.wx")
public class WxpayConfig {
    /**
     * appid（应用ID）
     * *
     */
    public String appid;
    /**
     * appsecret（应用秘钥）
     */
    public String secret;
    /**
     * mchid（商户ID）
     */
    public String mchId;
    /**
     * Key(支付秘钥)
     */
    public String key;
    /**
     * 商户证书
     */
    public String certFile;
    /**
     * 支付回调异步通知地址
     */
    public String notifyUrl;
    /**
     * 退款异步通知地址<br>
     * 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。
     */
    public String refundNotifyUrl;
    /**
     * sub_appid（子应用ID）
     */
    public String subAppid;
    /**
     * sub_mch_id（子商户ID）
     */
    public String subMchId;
    /**
     * 获取子商户号Key(支付秘钥)
     */
    public String subKey;
    /**
     * 子商户证书
     */
    public String subCertFile;
}
