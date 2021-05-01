---
category: 三要素鉴权
order: 1
title: 三要素鉴权查询接口
---

根据订单号查询

---

## 请求数据

| 字段       | 释义       | 必填 | 类型   | 说明                                 |
| ---------- | ---------- | ---- | ------ | ------------------------------------ |
| merchantNo | 商户编码   | Y    | string | 业务系统分配                         |
| orderId    | 请求流水号 | Y    | string | 32 位以内                            |
| orderDate  | 请求日期   | Y    | string | yyyyMMdd mchtNo+tranDate+tranNo 唯一 |

## 响应数据

| 字段        | 释义           | 必填 | 类型   | 说明                                           |
| ----------- | -------------- | ---- | ------ | ---------------------------------------------- |
| code        | 接口返回标识   | Y    | string |                                                |
| desc        | 描述           | N    | string |                                                |
| data        | 数据           | N    | json   |                                                |
| --transId   | 平台交易流水号 | Y    | string |                                                |
| --transDate | 平台交易日期   | Y    | string | 格式：yyyyMMdd                                 |
| --status    | 平台业务状态   | Y    | string | `SUCCESS`成功 `FAIL`失败 `UNKNOWN`交易结果未知 |
