---
order: 4
title: 开发指南
---

接入方调用 DEMO 下載请点击:[DEMO](http://kalvan.store) 提取码：xxxx

---

## 组装请求报文

1. 随机生成一个 16 位 AES key
2. 用 AES key 来加密(AES/ECB/PKCS5Padding)报文明文 Base64 编码
3. 使用平台的公钥对 AES key 进行加密保护,加密算法 RSA/ECB/PKCS1Padding
4. 使用商户的私钥对报文明文进行签名,签名算法 SHA1WithRSA

> 接入方请求报文

```
{
   "appId":"" ,
   "service":"",
   "key":"",
   "data":"",
   "sign":"",
   "msgId":""
}
```

> `data`对应明文

```
{
 "orderId":"1000001",
 "orderDate":"20200601"
}
```

## 解析返回报表

1. 先用商户私钥解密 data 里面的 key 得到 AES key
2. 用 AES key 解密得到明文
3. 用商户公钥验证签名

> 平台返回报文

```
{
    "key":"",
    "content":"",
    "sign": ""
}
```

> 解密`content`后得到明文

```
{
    "code": "0000",
    "desc": "处理成功",
    "data": {
        "xx1": 1,
        "xx2": 2
    }
}
```
