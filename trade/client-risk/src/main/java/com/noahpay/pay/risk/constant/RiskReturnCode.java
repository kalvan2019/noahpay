package com.noahpay.pay.risk.constant;

import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.model.ReturnCode;

/**
 * 系统返回码定义
 *
 * @author chenliang
 */
public interface RiskReturnCode extends CommonReturnCode {
    //总风控
    ReturnCode CODE_5401 = new ReturnCode("5401", "禁止提现");

    //黑名单
    ReturnCode CODE_5402 = new ReturnCode("5402", "客户号被风控");
    ReturnCode CODE_5403 = new ReturnCode("5403", "身份证号被风控");
    ReturnCode CODE_5404 = new ReturnCode("5404", "银行账号被风控");
    ReturnCode CODE_5405 = new ReturnCode("5405", "客户邮箱被风控");
    ReturnCode CODE_5406 = new ReturnCode("5406", "手机号被风控");
    ReturnCode CODE_5407 = new ReturnCode("5407", "营业执照注册号被风控");
    /**
     * 交易
     */
    ReturnCode CODE_5420 = new ReturnCode("5420", "日分配笔数已满");
    ReturnCode CODE_5421 = new ReturnCode("5421", "日分配额度已满");
    ReturnCode CODE_5422 = new ReturnCode("5422", "月分配笔数已满");
    ReturnCode CODE_5423 = new ReturnCode("5423", "月分配额度已满");
}
