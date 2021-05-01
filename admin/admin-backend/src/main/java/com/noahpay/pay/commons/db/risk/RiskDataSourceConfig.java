package com.noahpay.pay.commons.db.risk;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.system.SystemUtil;
import com.noahpay.pay.commons.db.DataSourceConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/***
 * risk数据源加载扫描
 *
 * @author kalvan.tools:kalvan
 */
@Configuration
@MapperScan(basePackages = DataSourceConstants.RISK_MAPPER_PACKAGE, sqlSessionFactoryRef = DataSourceConstants.RISK_SESSION_FACTORY)
public class RiskDataSourceConfig {
    @Resource
    NacosConfigManager nacosConfigManager;

    @DependsOn(SystemUtil.SYSTEM_UTIL)
    @Bean
    public DataSource riskDataSource() throws SQLException, IOException {
        String configData;
        try {
            configData = nacosConfigManager.getConfigService().getConfig(DataSourceConstants.RISK_SHARDING_YML, "DEFAULT_GROUP", 3000);
        } catch (NacosException e) {
            throw new BizException(CommonReturnCode.ERROR.formatMessage("数据源配置加载失败" + DataSourceConstants.RISK_SHARDING_YML));
        }
        if (StringUtils.isBlank(configData)) {
            throw new BizException(CommonReturnCode.ERROR.formatMessage("数据源配置未找到" + DataSourceConstants.RISK_SHARDING_YML));
        }
        return YamlShardingDataSourceFactory.createDataSource(configData.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public SqlSessionFactory riskSessionFactory(@Qualifier(DataSourceConstants.RISK_DATA_SOURCE) DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(DataSourceConstants.RISK_MAPPER_PATH));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate riskSqlSessionTemplate(
            @Qualifier(DataSourceConstants.RISK_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager riskTransactionManager(@Qualifier(DataSourceConstants.RISK_DATA_SOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
