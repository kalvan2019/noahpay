---
order: 5
title: 接口规范
---

## 接入环境

| 环境     | 地址                           |
| -------- | ------------------------------ |
| SIT 测试 | https://openapi.sit.kalvan.com |
| UAT 测试 | https://openapi.uat.kalvan.com |
| 生产     | https://openapi.kalvan.com     |

## 调用协议

| 说明项   | 说明                           |
| -------- | ------------------------------ |
| 通讯方式 | https post 表单提交            |
| 数据格式 | json                           |
| 字符编码 | UTF-8                          |
| 加密算法 | AES-256 `AES/ECB/PKCS5Padding` |
| 签名算法 | RSA `SHA1WithRSA`              |

## 公共参数

> 接口公共参数为对接开放平台接口请求和返回必须包含的字段

### 公共请求参数

| 字段    | 释义           | 必填 | 类型   | 说明                                                 |
| ------- | -------------- | ---- | ------ | ---------------------------------------------------- |
| appId   | 接入方 ID      | Y    | string | 平台提供                                             |
| service | 接入具体接口名 | Y    | string | 可通过 url 传参                                      |
| key     | 随机 AES KEY   | Y    | string | 使用平台公钥`RSA/ECB/PKCS1Padding`加密保护           |
| data    | 业务数据       | Y    | json   | 业务数据，使用 AES KEY`AES/ECB/PKCS5Padding`加密保护 |
| sign    | 签名数据       | Y    | string | 使用接入方私钥进行签名整个数据包                     |
| msgId   | 请求报文 Id    | Y    | string |                                                      |

**_`data`明文示例_**

```
{
  "merchantNo": "10100001",
  "acctNo": "6216261000000000118",
  "acctName": "张三",
  "certNo": "341126197709218366",
  "certType": "ID_CARD",
  "orderId": "1000001",
  "orderDate": "20200910",
  "verifyType": "RZ03"
}
```

### 公共返回参数

| 字段 | 释义     | 必填 | 类型   | 说明                                                      |
| ---- | -------- | ---- | ------ | --------------------------------------------------------- |
| code | 返回码   | Y    | string | 仅做为接口请求成功失败的判断依据,不做为业务处理结果的依据 |
| desc | 返回说明 | Y    | string |                                                           |
| data | 返回数据 | N    | json   |                                                           |

**_返回明文示例_**

```
{
    "code": "0000",
    "desc": "请求成功",
    "data": {
        "transId":"10120200910001",
        "transDate":"20200910",
        "status":SUCCESS"
    }
}
```
