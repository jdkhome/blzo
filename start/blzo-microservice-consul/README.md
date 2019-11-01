# BLZO 微服务项目脚手架

blzo-microservice 适用于中大型项目 , 后续会补一个k8s整合版

blzo-microservice-consul 是以consul作为注册中心的脚手架具体实现。  
用本脚手架开始你的项目，除了blzo脚手架的基础功能外，你将直接获得：

- 注册中心、服务发现(基于consul)
- 配置中心(可选)(基于consul)
- 分布式定时任务(可选)(基于[blzo-ex-task](https://www.jdkhome.com/blzo-ex/blzo-ex-task.html))
- 分布式锁(基于 redis -> redission -> [blzo-ex-redission](https://www.jdkhome.com/blzo-ex/blzo-ex-redission.html))
- 分布式事务([seata](https://github.com/seata/seata))
- 推送中心(可选)(基于 mqtt -> emq -> [blzo-ex-mqtt](https://www.jdkhome.com/blzo-ex/blzo-ex-mqtt.html))

基本已涵盖整合分布式场景下所有基础需求

> \*更多实用服务 持续整合中...

## 快速开始

1. 准备 Mysql Redis Consul [Seata]() 服务
1. 创建 [管理后台基础数据库](./doc/manage.md) [user模块和friend模块数据库](doc/miroservice.md) 
2. 修改服务链接配置，启动项目自由探索！



