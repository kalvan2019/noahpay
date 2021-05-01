---
category: 扫码支付
order: 0
title: 概览
---

为满足持卡人、商户对方便快捷的支付需求，设计了二维码支付业务。持卡人仅需使用 APP 通过商户或持卡人扫一扫功能，即可完成支付。本产品适用于线下各类商户，如餐饮、娱乐、商超等。

---

## 场景介绍

接入方开发微信小程序服务，在小程序中提供支付功能

## 交易流程

主要步骤

1. 接入方通过调用微信小程序接口，获取发起支付的小程序用户 id，即 openId

2. 接入方生成业务订单

3. 使用订单号、wxSubAppId（小程序 id） 和 openId（小程序对应的用户 id）及其他参数调用 preCreate 接口 https://kalvan.store/product/doc?prodId=17&docType=2&docId=783 预创建订单 ； 注意：wxSubAppId 和 openId 两字段必须是匹配的， 即小程序 id 和当前支付用户（小程序用户）的 openId

4. 如果需要支付结果回调，请实现回调接口， 并在 preCreate 参数 notifyUrl 指定回调地址

5. 使用 preCreate 返回的参数调起微信支付收银台。 调起微信收银台可选方案： 1、 使用 H5 SDK 调起微信支付弹窗 https://kalvan.store/product/doc?prodId=17&docType=2&docId=282 ， 2、接入方直接使用微信提供的 API 调起微信支付弹窗（需要通过微信的验证授权）

6. 支付回调获得当前订单状态，或调用订单查询接口https://kalvan.store/product/doc?prodId=17&docType=2&docId=826获取订单状态

![](https://note.youdao.com/yws/api/personal/file/WEB416619882545265a6d4ea4a9b8f555ad?method=download&shareKey=7759b0f54e90123a71101088dbbceda1)

## FAQ

1. 授权之后的返回 URL 没有带上 openId

   > 获取 openId 分为两步。1.获取 code/auth_code ；2.通过 code/auth_code 获取 openId
   >
   > 所以当返回 URL 没有带上 openId 时，有可能是这两步中的任何一部出错
   >
   > 原因一：没有获取到 code/auth_code ，请先确认 redirecit_uri 参数是否有#符号，经过过往经验，此种情况会导致获取不到 code/auth_code ，若确认没有#之后还有问题，请及时联系我们
   >
   > 原因二：商户不合法，建议接入方先确认接入的服务类型，服务类型调用错误。

2. 授权之后跳转到 redirect_uri 之后，url 中原有的 queryString 参数缺失

   > 原因：没有将 redirect_uri 地址进行 encode,请使用 encodeURIComponent 进行编码

3. 微信完成支付后的回调参数 payStatus 为 cancel，但是通过订单查询接口显示已经付款成功

   > 客户端返回的支付状态不能保证完全正确，具体请参考 微信支付 SDK 文档

4. 支付成功后没有调整到 redirect_uri，而是停留在 openpay 域名下，并展示 404 Not Found

   > 请确认 redirect_uri 是否存在两次 encode 的情况，另外使用 SDK 方式接入时，不需要进行 encode

5. 微信里跳转到 redirect_uri 时，为什么需要一次 302 跳转

   > 微信公众号支付需要设置白名单，如果没有 302 跳转，则被微信视为不合法, 请参考： 微信支付 SDK 文档

6. 无法唤起微信支付弹窗
   > 开放支付公众号授权地址为：https://kalvan.store/pay/?xxxx ，请确认 pay 后面是否已添加 /，否则无法唤起微信支付, 请参考：微信支付 SDK 文档
