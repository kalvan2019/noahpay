package com.noahpay.pay.commons.db.cust.mapper;

import com.noahpay.pay.commons.db.cust.model.CustInfo;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 客户信息Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface CustInfoMapper extends IBaseMapper<CustInfo> {

    /**
     * 唯一键查询
     *
     * @param certificateNo   certificateNo
     * @param certificateType certificateType
     * @param partTable       partTable
     * @return CustInfo
     */
    CustInfo getCustInfoByUk(@Param("certificateNo") String certificateNo, @Param("certificateType") Integer certificateType, @Param("partTable") String partTable);

}
