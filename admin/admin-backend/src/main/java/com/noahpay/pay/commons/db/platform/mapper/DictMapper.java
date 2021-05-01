package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.Dict;
import com.kalvan.db.mybatis.IBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字典配置Mapper
 *
 * @author kalvan.tools:kalvan
 * @date 2020-08-23 15:07:36
 */
@Repository
public interface DictMapper extends IBaseMapper<Dict> {

    /**
     * 查找所有字典类型
     * @return
     */
    List<Dict> selectAllType();

    /**
     * 查找指定类型下所有字典
     * @param dictType
     * @return
     */
    List<Dict> selectDictByType(String dictType);
}
