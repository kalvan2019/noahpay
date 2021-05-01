---
category: 扫码支付
type: 被扫
order: 3
title: 二维码预授权
---

持卡人被商户用扫描枪扫码，发起预授权。预授权交易用于受理方向持卡人的发卡方确认交易许可。受理方将预估的消费金额作为预授权金额，发送给持卡人的发卡方。

---

## 请求数据

| 字段       | 释义           | 必填    | 类型            | 说明                                 |
| ---------- | -------------- | ------- | --------------- | ------------------------------------ |
| merchantNo | 商户编码       | Y       | string          | 业务系统分配                         |
| acctNo     | 银行卡账号     | Y       | string          |
| acctName   | 银行卡账户名称 | Y       | string          |
| certNo     | 证件号码       | Y       | string          |                                      |
| certType   | 证件类型       | Ystring | 固定值：ID_CARD |
| orderId    | 请求流水号     | Y       | string          | 32 位以内                            |
| orderDate  | 请求日期       | Y       | string          | yyyyMMdd mchtNo+tranDate+tranNo 唯一 |
| verifyType | 验证类型       | Y       | string          | 三要素:RZ03                          |

## 响应数据

| 字段        | 释义           | 必填 | 类型   | 说明                                           |
| ----------- | -------------- | ---- | ------ | ---------------------------------------------- |
| code        | 接口返回标识   | Y    | string |                                                |
| desc        | 描述           | N    | string |                                                |
| data        | 数据           | N    | json   |                                                |
| --transId   | 平台交易流水号 | Y    | string |                                                |
| --transDate | 平台交易日期   | Y    | string | 格式：yyyyMMdd                                 |
| --status    | 平台业务状态   | Y    | string | `SUCCESS`成功 `FAIL`失败 `UNKNOWN`交易结果未知 |

## 返回码
