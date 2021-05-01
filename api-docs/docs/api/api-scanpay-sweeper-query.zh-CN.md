---
category: 扫码支付
type: 主扫
order: 2
title: 交易状态查询接口
---

对于未收到交易结果的联机交易，商户应向银联全渠道支付平台发起交易状态查询交易，查询交易结果。完成交易的过程不需要同持卡人交互，属于后台交易。交易查询类交易可由商户通过 SDK 向银联全渠道支付交易平台发起交易。

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
