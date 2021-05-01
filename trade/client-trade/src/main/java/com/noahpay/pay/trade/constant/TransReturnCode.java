package com.noahpay.pay.trade.constant;

import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.model.ReturnCode;

/**
 * 系统返回码定义
 *
 * @author chenliang
 */
public interface TransReturnCode extends CommonReturnCode {
    ReturnCode CODE_MANUAL = new ReturnCode("manual", "人工重置{0}");
    ReturnCode CODE_2402 = new ReturnCode("2402", "计费失败{0}");
    ReturnCode CODE_2403 = new ReturnCode("2403", "路由失败{0}");
    ReturnCode CODE_2404 = new ReturnCode("2404", "风控阻断{0}");
    ReturnCode CODE_2405 = new ReturnCode("2405", "记账失败{0}");
    ReturnCode CODE_2406 = new ReturnCode("2406", "支付协议检查失败{0}");
    ReturnCode CODE_2407 = new ReturnCode("2407", "消费人和收款人不匹配");
    ReturnCode CODE_2408 = new ReturnCode("2408", "商户余额不足");
    ReturnCode CODE_2409 = new ReturnCode("2409", "通知失败");
    ReturnCode CODE_2410 = new ReturnCode("2410", "通道参数转换失败{0}");
    ReturnCode CODE_2411 = new ReturnCode("2411", "卡信息鉴权失败{0}");
    ReturnCode CODE_2412 = new ReturnCode("2412", "已退票，请勿重复操作！");
    ReturnCode CODE_2413 = new ReturnCode("2413", "退票失败,{}");
    ReturnCode CODE_2414 = new ReturnCode("2414", "分润统计处理失败,{}");
    ReturnCode CODE_2415 = new ReturnCode("2415", "该卡未绑卡，请您先绑卡后交易");
    ReturnCode CODE_2416 = new ReturnCode("2416", "原订单状态不允许发起退款");
    ReturnCode CODE_2701 = new ReturnCode("2701", "通道状态未知");
    ReturnCode CODE_2702 = new ReturnCode("2702", "通道失败");
    ReturnCode CODE_2703 = new ReturnCode("2703", "银行卡余额不足");
    ReturnCode CODE_2704 = new ReturnCode("2704", "银行卡信息有误");
    ReturnCode CODE_2705 = new ReturnCode("2705", "通道金额不匹配");
    ReturnCode CODE_2706 = new ReturnCode("2706", "通道头寸不足");
    ReturnCode CODE_2303 = new ReturnCode("2303", "账户系统繁忙");
    ReturnCode CODE_2304 = new ReturnCode("2304", "客户系统繁忙");
    ReturnCode CODE_2305 = new ReturnCode("2305", "风控系统繁忙");
    ReturnCode CODE_2306 = new ReturnCode("2306", "通道系统繁忙");
    ReturnCode CODE_2307 = new ReturnCode("2307", "路由系统繁忙");
}
