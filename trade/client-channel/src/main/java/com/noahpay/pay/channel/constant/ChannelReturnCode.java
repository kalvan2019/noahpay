package com.noahpay.pay.channel.constant;

import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.model.ReturnCode;

/**
 * 系统返回码定义
 *
 * @author chenliang
 */
public interface ChannelReturnCode extends CommonReturnCode {
    /**
     * 异常返回
     * 60xx 非法访问
     * 61xx 参数异常
     * 62xx 查询异常
     * 63xx 连接异常
     * 64xx 业务异常
     * 65xx db异常
     * 66xx 未知异常
     */
    ReturnCode CODE_6000 = new ReturnCode("6000", "非法请求");
    ReturnCode CODE_6001 = new ReturnCode("6001", "未实现方法{0}");
    ReturnCode CODE_6101 = new ReturnCode("6101", "参数不能为空{0}");
    ReturnCode CODE_6102 = new ReturnCode("6102", "参数不合法{0}");
    ReturnCode CODE_6103 = new ReturnCode("6103", "{0}超过范围{1}");
    ReturnCode CODE_6201 = new ReturnCode("6201", "查无此记录{0}");
    ReturnCode CODE_6301 = new ReturnCode("6301", "连接通道超时");
    ReturnCode CODE_6405 = new ReturnCode("6405", "请求限流");
    ReturnCode CODE_6406 = new ReturnCode("6406", "银行卡余额不足");
    ReturnCode CODE_6407 = new ReturnCode("6407", "银行卡信息有误");
    ReturnCode CODE_6408 = new ReturnCode("6408", "头寸不足");
    ReturnCode CODE_6409 = new ReturnCode("6409", "通道失败");
    ReturnCode CODE_6410 = new ReturnCode("6410", "组装通道报文异常");
    ReturnCode CODE_6411 = new ReturnCode("6411", "{0}通道回执异常");
    ReturnCode CODE_6412 = new ReturnCode("6412", "无可用路由 {0}");
    ReturnCode CODE_6413 = new ReturnCode("6413", "加签异常 {0}");
    ReturnCode CODE_6414 = new ReturnCode("6414", "验签异常 {0}");
    ReturnCode CODE_6415 = new ReturnCode("6415", "风控异常 {0}");
    ReturnCode CODE_6416 = new ReturnCode("6416", "其他模块系统异常，{0}");
    ReturnCode CODE_6417 = new ReturnCode("6417", "通道：{0}未配置计费");
    ReturnCode CODE_6418 = new ReturnCode("6418", "系统维护中，请联系客服！");
    ReturnCode CODE_6501 = new ReturnCode("6501", "数据库异常");
    ReturnCode CODE_6601 = new ReturnCode("6601", "系统异常{0}");
}
