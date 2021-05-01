# channel-sdk

#### 介绍
微信支付：
1.支付产品：付款码支付、扫码支付、APP支付、公众号支付、H5支付、小程序支付；
2.支付工具：代金券、现金红包、企业付款；
3.网页授权：获取openid、用户信息、JS-SDK签名、微信分享等；
4.小程序授权登录：获取openid、用户信息、用户手机号等。
5. 微信小程序支付（1.2.1版本）、授权登录、解密获取用户信息、手机号信息（1.3.0版本）。
6. 支付宝支付相关对官方的SDK进行了封装。
7. 银联在线网关支付（1.1版本）。

#### 使用说明

1. 实现并注入微信（IWxpayConfig）/ 支付宝（IAlipayConfig）/ 银联支付（IUnionpayConfig）配置接口Bean

    以下步骤以微信支付为例：
```
	@Bean
	public IWxpayConfig wxpayConfig(){
	    return new IWxpayConfig() {

		@Override
		public String secret() {
		    return "secret";
		}

		@Override
		public String refundNotifyUrl() {
		    return "refundNotifyUrl";
		}

	    @Override
		public String notifyUrl() {
		    return "notifyUrl";
		}

		@Override
		public String mchId() {
		    return "mchId";
		}

	    @Override
		public String subMchId() {
		    return "subMchId";
		}

		@Override
		public String key() {
		    return "key";
		}

		@Override
		public String subKey() {
		    return "subKey";
		}

		@Override
		public String certFile() {
		    return "/certFile";
		}

		@Override
		public String subCertFile() {
		    return "/subCertFile";
		}

		@Override
		public String appid() {
		    return "appid";
		}

		@Override
		public String subAppid() {
		    return "subAppid";
		}
	    };
	}
```

**PS:**  :exclamation: 微信小程序开发支付服务商版情况下，需要在小程序的appid加个前缀 `mini:`。

```
        @Override
		public String subAppid() {
		    return WxpayConstants.MINI + "miniAppid";
		}
```
2. 注入请求客户端Bean

```
         @Bean
         public WxpayClient wxpayClient() throws WxpayApiException {
            InitWxpayClient wxpayClient = new InitWxpayClient();
            wxpayClient.setWxpayConfig(wxpayConfig());              
            wxpayClient.setOnlyAuth(false);  // false：支付请求客户端，true：网页授权请求客户端	
            wxpayClient.setServiceProvider(true); // false：普通商户，true：服务商
            wxpayClient.setName("wxpay-01"); // 客户端名称
	        return wxpayClient.build();
         }
```

3. 注入Wxpay业务Bean

```
        @Bean
        public Wxpay wxpay() throws WxpayApiException {
            return new Wxpay(wxpayConfig(), wxpayClient());
        }
```

4. 自动装配Wxpay

```
        @Autowired
        private Wxpay wxpay;
```
以统一下单为例：


```
        WxpayUnifiedorder unifiedorder = new WxpayUnifiedorder();
        unifiedorder.setBody("测试商品名称");
        unifiedorder.setOut_trade_no("DD201907101400845");
        unifiedorder.setTotal_fee("1");
        unifiedorder.setTrade_type(WxpayTradeType.NATIVE);
        WxpayUnifiedorderResponse  response = wxpay.unifiedorder(unifiedorder);
        if (response.isSuccess()) { // 微信业务请求成功			
	    if(WxpayConstants.SUCCESS.equals(response.getResultCode())) {
             // 统一下单成功
	     // 自己的业务代码
	    }
        }
```


    

#### 参与贡献

1. 微信支付官方文档：[https://pay.weixin.qq.com/wiki/doc/api/index.html](https://pay.weixin.qq.com/wiki/doc/api/index.html)
2. 微信公众号开发（网页授权）官方文档：[https://mp.weixin.qq.com/wiki](https://mp.weixin.qq.com/wiki)
3. 微信小程序开发官方文档：[https://developers.weixin.qq.com/miniprogram/dev/framework/](https://developers.weixin.qq.com/miniprogram/dev/framework/)
4. 微信小程序请求参数文档：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/uniform-message/uniformMessage.send.html
5. 支付宝支付API官方文档：[https://docs.open.alipay.com/api](https://docs.open.alipay.com/api)
6. 银联支付API官方文档：[https://open.unionpay.com/tjweb/api/list](https://open.unionpay.com/tjweb/api/list)


