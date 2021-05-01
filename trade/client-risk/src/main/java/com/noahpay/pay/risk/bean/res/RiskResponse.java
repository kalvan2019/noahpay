package com.noahpay.pay.risk.bean.res;

import com.noahpay.pay.risk.constant.RiskLevelEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 检查风控返回对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class RiskResponse implements java.io.Serializable {
    /**
     * 风控等级
     *
     * @see RiskLevelEnum
     */
    private Integer riskLevel;
    /**
     * 关联风险事件id
     */
    private String refTransId;
    /**
     * 风控参数
     */
    private String riskParams;
    /**
     * 风控原因
     */
    private String riskReason;
}
