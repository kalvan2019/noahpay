---
order: 0
title: 准备工作
---

运行环境准备、开发工具安装、中间件安装、demo程序下载配置运行

---

## 🚚运行环境

| -    | 说明         | 版本                                                       |
| ---- | ------------ | ---------------------------------------------------------- |
| node | 前端运行环境 | [11.6.0](https://mirrors.huaweicloud.com/nodejs/v11.6.0/)  |
| java | 后端运行环境 | [1.8](https://mirrors.huaweicloud.com/java/jdk/8u171-b11/) |

## 📦开发工具

| -          | 说明         | 版本                                                                          |
| ---------- | ------------ | ----------------------------------------------------------------------------- |
| maven      | 后端编译构建 | [3.5.3](https://mirrors.huaweicloud.com/apache/maven/maven-3/3.5.3/binaries/) |
| git        | 代码版本控制 | [-](https://mirrors.huaweicloud.com/git-for-windows/)                         |
| idea       | 开发工具     | [2020.2.1](https://www.jetbrains.com/idea/download/)                          |
| lombok     | 开发插件     | [0.31](https://plugins.jetbrains.com/plugin/6317-lombok/)                     |
| kalvan-gen | 开发插件     | [1.2](https://plugins.jetbrains.com/plugin/15002-kalvan-gen)                  |

## ⚙中间件

| 服务               | 服务名         | 端口                                                | 版本                                                                                                                                                                              |
| ------------------ | -------------- | --------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 数据库             | mysql          | 3306                                                | [5.7.22](https://mirrors.huaweicloud.com/mysql/Downloads/MySQL-5.7/)                                                                                                              |
| 缓存               | redis          | 6379                                                | [4.0.8](https://mirrors.huaweicloud.com/redis/redis-4.0.8.tar.gz) \| [window 版本](https://github.com/microsoftarchive/redis/releases/download/win-3.2.100/Redis-x64-3.2.100.msi) |
| 注册与配置中心     | nacos          | [8848](http://127.0.0.1:8848/nacos)                 | [1.3.2](https://github.com/alibaba/nacos/releases)                                                                                                                                |
| job 注册中心       | zookeeper      | 2181 \|控制台[8888](http://127.0.0.1:8888/commands) | [3.6.2](https://zookeeper.apache.org/releases.html)                                                                                                                               |
| 流量控制、熔断降级 | Sentinel       | [8080](http://127.0.0.1:8080)                       | [下载](http://edas-public.oss-cn-hangzhou.aliyuncs.com/install_package/demo/sentinel-dashboard.jar)                                                                               |
| 消息中间件         | rocketmq       | 9876                                                | [4.4.0](https://archive.apache.org/dist/rocketmq/4.4.0/rocketmq-all-4.4.0-bin-release.zip)                                                                                        |
| 搜索引擎存储中间件 | elasticsearch  | [9200](http://127.0.0.1:9200)                       | [7.8.0](https://mirrors.huaweicloud.com/elasticsearch/7.8.0/)                                                                                                                     |
| 日志收集           | logstash       | [9600](http://127.0.0.1:9600)                       | [7.8.0](https://mirrors.huaweicloud.com/logstash/7.8.0/)                                                                                                                          |
| 日志可视化         | kibana         | [5601](http://127.0.0.1:5601)                       | [7.8.0](https://mirrors.huaweicloud.com/kibana/7.8.0/)                                                                                                                            |
| 链路跟踪           | SkyWalking APM | [18080](http://127.0.0.1:18080)                     | [8.1.0](https://mirrors.tuna.tsinghua.edu.cn/apache/skywalking/8.1.0/apache-skywalking-apm-es7-8.1.0.tar.gz)                                                                      |
| 调度任务控制台     | elasticjob     | 后端 8088 \|前端[8087](http://127.0.0.1:8087)       | [下载](https://github.com/apache/shardingsphere-elasticjob-ui/tree/master/shardingsphere-elasticjob-lite-ui)                                                                      |
| 接口文档管理       | yapi           | [3005](http://127.0.0.1:3005)                       | [下载](https://gitee.com/mirrors/YApi?_from=gitee_search)                                                                                                                         |
| 服务监控系统       | prometheus     | [9090](http://127.0.0.1:9090)                       | [2.21.0](https://mirrors.tuna.tsinghua.edu.cn/github-release/prometheus/prometheus/2.21.0%20_%202020-09-11/)                                                                      |  |
| 可视化工具         | grafana        | [3000](http://127.0.0.1:3000)                       | [7.1.5](https://mirrors.huaweicloud.com/grafana/7.1.5/)                                                                                                                           |

## ❤下载源码

***kalvan-admin***
```
git clone https://gitee.com/spring_thunder/kalvan-admin
```
***kalvan-demo***
```
git clone https://gitee.com/spring_thunder/kalvan-demo
```

## ✨数据脚本

1. 执行`kalvan-admin/kalvan-admin-backend/db/` 下的脚本
2. 执行`kalvan-demo/db/` 下的脚本


## 🌈️应用列表

> 根据自己需要，启动相应服务进行测试。启动服务前请先启动依赖的中间件
>

| 服务名                                 | 简介      | 应用地址              |
| -------------------------------------- | --------- | --------------------- |
| [eureka](docs/learn/demo-eureka)       | 注册中心(不使用nacos时可选)| http://localhost:9001 |
| [gateway](docs/learn/demo-gateway)     | 权限控制  | http://localhost:9002 |
| [api](docs/learn/demo-api)             | 接口网关  | http://localhost:9003 |
| [signature](docs/learn/demo-signature) | 签名&加密 | http://localhost:9004 |
| [auth](docs/learn/demo-auth)           | 权限控制  | http://localhost:9005 |
| [trade](docs/learn/demo-trade)         | 交易      | http://localhost:9006 |
| [notify](docs/learn/demo-notify)       | 通知推送  | http://localhost:9007 |
| [job](docs/learn/demo-job)             | 调度任务  | http://localhost:9008 |
| [monitor](docs/learn/demo-monitor)     | 服务监控  | http://localhost:9009 |
| [kalvan-admin-frontend](docs/learn/kalvan-admin)| 管理系统前端 | http://localhost:9901/kalvan-admin |
| [kalvan-admin-backend](docs/learn/kalvan-admin)| 管理系统后端 | http://localhost:9902            |

