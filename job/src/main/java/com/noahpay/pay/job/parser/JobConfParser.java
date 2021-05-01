package com.noahpay.pay.job.parser;

import com.kalvan.job.annotation.ElasticJobConf;
import com.kalvan.job.parser.JobAttributeTag;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Job解析类
 *
 * <p>
 * 从注解中解析任务信息初始化
 * <p>
 *
 * @author chenliang
 */
@Component
public class JobConfParser implements ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(JobConfParser.class);
    private final String prefix = "elasticJob.";
    @Resource
    private CoordinatorRegistryCenter zookeeperRegistryCenter;
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        environment = ctx.getEnvironment();
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(ElasticJobConf.class);
        for (Object jobBean : beanMap.values()) {
            Class<?> clz = jobBean.getClass();
            try {
                // 支持父类做模板实现job接口
                String jobTypeName = null;
                if (jobBean.getClass().getInterfaces().length > 0) {
                    jobTypeName = jobBean.getClass().getInterfaces()[0].getSimpleName();
                } else {
                    jobTypeName = jobBean.getClass().getSuperclass().getInterfaces()[0].getSimpleName();
                }
                String jobClass = clz.getName();
                ElasticJobConf conf = clz.getAnnotation(ElasticJobConf.class);
                // 根据表名是否传入，区分根据那种方式加载配置
                String tableName = conf.tableName();
                String jobName = conf.name();
                if (StringUtils.isEmpty(jobName)) {
                    jobName = clz.getSimpleName();
                }
                String cron = getEnvironmentStringValue(jobName, JobAttributeTag.CRON, conf.cron());
                String description = getEnvironmentStringValue(jobName, JobAttributeTag.DESCRIPTION, conf.description());
                String shardingItemParameters = getEnvironmentStringValue(jobName, JobAttributeTag.SHARDING_ITEM_PARAMETERS,
                        conf.shardingItemParameters());
                int shardingTotalCount = getEnvironmentIntValue(jobName, JobAttributeTag.SHARDING_TOTAL_COUNT,
                        conf.shardingTotalCount());
                String jobParameter = getEnvironmentStringValue(jobName, JobAttributeTag.JOB_PARAMETER,
                        conf.jobParameter());
                String jobExceptionHandler = getEnvironmentStringValue(jobName, JobAttributeTag.JOB_EXCEPTION_HANDLER,
                        conf.jobExceptionHandler());
                String executorServiceHandler = getEnvironmentStringValue(jobName,
                        JobAttributeTag.EXECUTOR_SERVICE_HANDLER, conf.executorServiceHandler());
                String jobShardingStrategyClass = getEnvironmentStringValue(jobName,
                        JobAttributeTag.JOB_SHARDING_STRATEGY_CLASS, conf.jobShardingStrategyClass());
                boolean failover = getEnvironmentBooleanValue(jobName, JobAttributeTag.FAILOVER, conf.failover());
                boolean misfire = getEnvironmentBooleanValue(jobName, JobAttributeTag.MISFIRE, conf.misfire());
                boolean overwrite = getEnvironmentBooleanValue(jobName, JobAttributeTag.OVERWRITE, conf.overwrite());
                boolean disabled = getEnvironmentBooleanValue(jobName, JobAttributeTag.DISABLED, conf.disabled());
                boolean monitorExecution = getEnvironmentBooleanValue(jobName, JobAttributeTag.MONITOR_EXECUTION,
                        conf.monitorExecution());
                int maxTimeDiffSeconds = getEnvironmentIntValue(jobName, JobAttributeTag.MAX_TIME_DIFF_SECONDS,
                        conf.maxTimeDiffSeconds());
                int reconcileIntervalMinutes = getEnvironmentIntValue(jobName,
                        JobAttributeTag.RECONCILE_INTERVAL_MINUTES, conf.reconcileIntervalMinutes());
                String eventTraceRdbDataSource = getEnvironmentStringValue(jobName,
                        JobAttributeTag.EVENT_TRACE_RDB_DATA_SOURCE, conf.eventTraceRdbDataSource());
                String scriptCommandLine = getEnvironmentStringValue(jobName, JobAttributeTag.SCRIPT_COMMAND_LINE,
                        conf.scriptCommandLine());
                boolean streamingProcess = getEnvironmentBooleanValue(jobName, JobAttributeTag.STREAMING_PROCESS,
                        conf.streamingProcess());
                int monitorPort = getEnvironmentIntValue(jobName, JobAttributeTag.MONITOR_PORT, conf.monitorPort());
                // TODO 通过数据库配置个性化
                // 核心配置
                JobConfiguration jobConfiguration = JobConfiguration.newBuilder(jobName, shardingTotalCount)
                        .cron(cron)
                        .shardingItemParameters(shardingItemParameters)
                        .description(description)
                        .failover(failover)
                        .misfire(misfire)
                        .jobParameter(jobParameter)
                        .jobErrorHandlerType(jobExceptionHandler)
                        .jobExecutorServiceHandlerType(executorServiceHandler)
                        .jobShardingStrategyType(jobShardingStrategyClass)
                        .overwrite(overwrite)
                        .disabled(disabled)
                        .monitorExecution(monitorExecution)
                        .maxTimeDiffSeconds(maxTimeDiffSeconds)
                        .reconcileIntervalMinutes(reconcileIntervalMinutes)
                        .build();
                // 不同类型的任务配置处理
                List<BeanDefinition> elasticJobListeners = getTargetElasticJobListeners(conf);
                // 构建JobScheduler对象来初始化任务
                BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ScheduleJobBootstrap.class);
                factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
                factory.addConstructorArgValue(zookeeperRegistryCenter);
                factory.addConstructorArgValue(jobBean);
                factory.addConstructorArgValue(jobConfiguration);
                factory.addConstructorArgValue(elasticJobListeners);
                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
                defaultListableBeanFactory.registerBeanDefinition(jobName, factory.getBeanDefinition());
                ScheduleJobBootstrap springJobScheduler = (ScheduleJobBootstrap) ctx.getBean(jobName);
                springJobScheduler.schedule();
                logger.info(" {} {} init success", jobName, jobClass);
            } catch (Exception e) {
                logger.error(clz.getName(), e);
                throw e;
            }
        }
    }

    private List<BeanDefinition> getTargetElasticJobListeners(ElasticJobConf conf) {
        List<BeanDefinition> result = new ManagedList<BeanDefinition>(2);
        String listeners = getEnvironmentStringValue(conf.name(), JobAttributeTag.LISTENER, conf.listener());
        if (StringUtils.hasText(listeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(listeners);
            factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            result.add(factory.getBeanDefinition());
        }
        String distributedListeners = getEnvironmentStringValue(conf.name(), JobAttributeTag.DISTRIBUTED_LISTENER,
                conf.distributedListener());
        long startedTimeoutMilliseconds = getEnvironmentLongValue(conf.name(),
                JobAttributeTag.DISTRIBUTED_LISTENER_STARTED_TIMEOUT_MILLISECONDS, conf.startedTimeoutMilliseconds());
        long completedTimeoutMilliseconds = getEnvironmentLongValue(conf.name(),
                JobAttributeTag.DISTRIBUTED_LISTENER_COMPLETED_TIMEOUT_MILLISECONDS,
                conf.completedTimeoutMilliseconds());
        if (StringUtils.hasText(distributedListeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(distributedListeners);
            factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            factory.addConstructorArgValue(startedTimeoutMilliseconds);
            factory.addConstructorArgValue(completedTimeoutMilliseconds);
            result.add(factory.getBeanDefinition());
        }
        return result;
    }

    /**
     * 获取配置中的任务属性值，environment没有就用注解中的值
     *
     * @param jobName      任务名称
     * @param fieldName    属性名称
     * @param defaultValue 默认值
     */
    private String getEnvironmentStringValue(String jobName, String fieldName, String defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return value;
        }
        return defaultValue;
    }

    private int getEnvironmentIntValue(String jobName, String fieldName, int defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }

    private long getEnvironmentLongValue(String jobName, String fieldName, long defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return Long.parseLong(value);
        }
        return defaultValue;
    }

    private boolean getEnvironmentBooleanValue(String jobName, String fieldName, boolean defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
}
