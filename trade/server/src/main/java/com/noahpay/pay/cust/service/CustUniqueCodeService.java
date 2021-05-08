package com.noahpay.pay.cust.service;

import com.kalvan.db.sharding.algorithm.MerchantNoShardingAlgorithm;
import com.kalvan.db.sharding.algorithm.ShardingConfig;
import com.noahpay.pay.commons.db.cust.mapper.CustUniqueCodeMapper;
import com.noahpay.pay.commons.db.cust.model.CustUniqueCode;
import com.noahpay.pay.cust.constant.CustTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Slf4j
@Service
public class CustUniqueCodeService {

    @Resource
    CustUniqueCodeMapper custUniqueCodeMapper;

    /**
     * 获取客户系统编号
     *
     * @param custType 2位(客户类型)
     * @return 生成新的编号1位(分库)2位(表)2位(客户类型)7位(序号)
     */
    public Long getUniqueNoDefault(CustTypeEnum custType) {
        return getUniqueNo(ShardingConfig.DEFAULT_PART_LIBRARY, ShardingConfig.DEFAULT_PART_TABLES, custType);
    }

    /**
     * 获取客户系统编号
     *
     * @param partLibrary 分库1位
     * @param partTables  分表2位
     * @param custType    客户类型2位
     * @return 生成新的编号1位(分库)2位(表)2位(客户类型)7位(序号)
     */
    public Long getUniqueNo(String partLibrary, String partTables, CustTypeEnum custType) {
        CustUniqueCode query = new CustUniqueCode();
        query.setPartLibrary(Integer.parseInt(partLibrary));
        query.setPartTables(Integer.parseInt(partTables));
        query.setCustType(custType.code);
        CustUniqueCode custUniqueCode = custUniqueCodeMapper.selectByUkLock(query);
        if (custUniqueCode == null) {
            custUniqueCode = query;
            //未找到则新增一条初始化记录
            custUniqueCode.setMaxCode(0);
            custUniqueCodeMapper.insertSelective(custUniqueCode);
        } else {
            custUniqueCode.setMaxCode(custUniqueCode.getMaxCode() + 1);
            custUniqueCodeMapper.updateByUkSelective(custUniqueCode);
        }
        StringBuilder returnNo = new StringBuilder();
        //编号为
        returnNo.append(partLibrary)
                .append(partTables)
                .append(custType.code)
                .append(StringUtils.leftPad(String.valueOf(custUniqueCode.getMaxCode()), 7, "0"));
        log.info("生成编号no:{}", returnNo.toString());
        return Long.valueOf(returnNo.toString());
    }

    /**
     * 获取客户系统编号
     *
     * @param certificateNo 身份证号码
     * @return 生成新的编号1位(分库)2位(表)2位(客户类型)7位(序号)
     */
    public Long getCustNo(String certificateNo) {
        return getUniqueNo(ShardingConfig.DEFAULT_PART_LIBRARY, getCustPartTable(certificateNo), CustTypeEnum.CUST);
    }

    /**
     * 根据身份证号获取分表
     *
     * @param certificateNo 身份证号码
     * @return 2位分表数
     */
    public String getCustPartTable(String certificateNo) {
        int code = certificateNo.hashCode() & Integer.MAX_VALUE;
        int partTable = code % ShardingConfig.SPLIT_TABLE;
        return StringUtils.leftPad(String.valueOf(partTable), 2, "0");
    }

    /**
     * 获取客户系统编号
     *
     * @param merchantNo 商户编号
     * @return 生成新的编号1位(分库)2位(表)2位(客户类型)7位(序号)
     */
    public Long getSubMerchantNo(Long merchantNo) {
        return getUniqueNo(ShardingConfig.DEFAULT_PART_LIBRARY, MerchantNoShardingAlgorithm.getSubMerchantPartTable(merchantNo), CustTypeEnum.SUB_MERCHANT);
    }

}
