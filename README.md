jzo2o-foundations工程的相关文档：

- 需求文档：https://a1n7ptau867.feishu.cn/wiki/YHiiwGMqIiu0YxkBOqncohp9nem?from=from_copylink
- 接口文档：https://apifox.com/apidoc/shared-5daa336f-a7ed-440c-9299-a16babb1cb48



## 1. 工程总览

| 工程名                | 中文名称         | 职责                                       | 链接地址                                           |
| --------------------- | ---------------- | ------------------------------------------ | :------------------------------------------------- |
| jzo2o-gateway         | 网关             | 请求过虑、负载均衡、路由转发               |                                                    |
| jzo2o-framework       | 系统架构基础工程 | 提供系统架构封装的基础代码                 |                                                    |
| jzo2o-api             | 接口工程         | 提供服务之间远程调用接口的定义             |                                                    |
| jzo2o-foundations     | 运营基础服务     | 服务管理、区域管理等运行基础服务           | https://github.com/slx-world/jzo2o-foundations.git |
| jzo2o-customer        | 客户管理服务     | 用户管理、服务人员管理、机构人员管理       | https://github.com/slx-world/jzo2o-customer.git    |
| jzo2o-orders-manager  | 订单管理服务     | 下单、订单状态管理、订单信息管理           |                                                    |
| jzo2o-orders-seize    | 抢单服务         | 为服务人员和机构抢单提供服务               |                                                    |
| jzo2o-orders-dispatch | 派单服务         | 根据派单规则自动为服务人员、机构派送订单   |                                                    |
| jzo2o-orders-history  | 历史订单服务     | 订单冷热分离，历史订单查询、订单统计接口。 |                                                    |
| jzo2o-trade           | 交易服务         | 小程序支付、退款等，与第三方支付系统对接   |                                                    |
| jzo2o-market          | 营销活动服务     | 活动管理                                   |                                                    |
| jzo2o-publics         | 通用服务         | 提供上传、定位等通用服务                   |                                                    |



## 2. 开发工具

| 开发工具           | 版本号         | 安装位置 |
| ------------------ | -------------- | -------- |
| IntelliJ-IDEA      | 2021.x以上版本 | 个人电脑 |
| JDK                | 11.x           | 个人电脑 |
| Maven              | 3.6.x以上版本  | 个人电脑 |
| Git                | 2.37.x         | 个人电脑 |
| VMware-workstation | 16.x或17.x     | 个人电脑 |
| CentOS             | 7.x            | 虚拟机   |
| Docker             | 18.09.0        | 虚拟机   |
| MySQL              | 8.0.x          | docker   |
| Elasticsearch      | 7.17.7         | docker   |
| Kibana             | 7.17.7         | docker   |
| nacos              | 1.4.1          | docker   |
| rabbitmq           | 3.9.17         | docker   |
| redis              | 6.2.7          | docker   |
| xxl-job-admin      | 2.3.1          | docker   |
| nginx              | 1.12.2         | docker   |
| sentinel           | 1.8.5          | docker   |
| seata              | 1.5.2          | docker   |
| Canal              | 1.15           | docker   |



## 3. 项目背景

云岚到家项目是一个家政服务o2o平台，互联网+家政是继打车、外卖后的又一个风口，创业者众多，比如：58到家，天鹅到家等，o2o（Online To Offline）是将线下商务的机会与互联网的技术结合在一起，让互联网成为线下交易的前台，同时起到推广和成交的作用。



## 4. 运营模式

<img width="889" alt="企业微信截图_5d6a07c7-0f53-480e-9a6e-2d3c23df705d" src="https://github.com/user-attachments/assets/d401720c-77f2-4665-b9ba-acb232dc4403">


C2B2C:

在家政 O2O（Online to Offline，线上到线下）领域中，"Consumer to Business to Consumer"（C2B2C）描述了一个商业模式，消费者不仅可以通过平台获取家政服务，还有机会成为服务提供者。在这个背景下，C2B2C 模式通常指的是：

1. **消费者（Consumer）：**
   1. 最终的家庭用户，他们需要家政服务，例如清洁、保姆、维修等。
2. **企业（Business）：**
   1. 在家政 O2O 中，企业通常是在线平台，提供家政服务的中介。这些平台通过在线渠道为消费者提供了查找、预订、支付等服务，同时也可能为家政服务提供者提供了工作机会。
3. **消费者（家政服务提供者）：**
   1. 在 C2B2C 模式中，一些消费者也可以成为服务的提供者。这些个体可能是独立的家政服务专业人员，他们可以在家政 O2O 平台上注册，提供自己的服务，并被其他需要服务的消费者雇佣。

B2B2C:

代表着"Business to Business to Consumer"，即企业到企业到消费者的模式。家政服务平台作为中间商，通过与各种家政服务提供商（家政服务公司）合作，为消费者提供多样化的家政服务选择。

**B2B2C****与****C2B2C****的区别是：B2B2C中服务提供者是家政服务中介公司，在C2B2C中是服务提供者是拥有服务技能的服务人员（散户）。**

本项目结合了C2B2C和B2B2C模式，个人和家政服务中介都可以通过平台提供家政服务，如下图：

项目包括四个端：用户端(小程序)、服务端（app）、机构端(PC)、运营管理端(PC)，四个端对应四类用户角色：

家政需求方：通过用户端小程序完成在线预约下单、支付、评价、投诉、退款等操作。

家政服务人员：通过服务端APP完成在线接单、结算等操作。

家政服务公司：通过机构端完成在线接单、派单、投诉处理、结算等操作。

平台方：通过管理端完成服务人员管理、机构管理、订单管理、财务管理等操作，一笔完成的订单，结算时按照分成比例平台进行抽成。

![企业微信截图_f2d2cb04-fcfc-45ad-8e8c-fca0d91507b8](C:\Users\slx99\AppData\Local\Temp\MicrosoftEdgeDownloads\44cbe26d-a654-452f-93ec-58613dcdc338\企业微信截图_f2d2cb04-fcfc-45ad-8e8c-fca0d91507b8.png)



## 5. 业务流程

![whiteboard_exported_image](C:\Users\slx99\AppData\Local\Temp\MicrosoftEdgeDownloads\1d2ca7b0-f1e3-4a3b-adb8-87f2aa37d56e\whiteboard_exported_image.png)

核心流程：

1. 运营端在运营区域上架家政服务

比如：在北京上架 日常保洁、空调维修。

1. 用户端通过定位区域获取当前区域的服务项目，选择家政服务，下单、支付
2. 家政服务人员及家政服务公司（机构）通过平台抢单
3. 家政服务人员现场服务，平台跟踪管理整个服务过程。
4. 服务完成，用户评价、售后服务等。



## 6. 业务模块

![小智帮结构图@2x](C:\Users\slx99\AppData\Local\Temp\MicrosoftEdgeDownloads\15d1ccf8-18c4-4114-9509-ce638891a83a\小智帮结构图@2x.png)

我们根据业务流程去分析各个模块的功能：

服务管理：对家政服务项目进行管理，最后在指定区域上架服务后用户可在当前区域购买。

下单支付：用户通过小程序完成下单支付，进入小程序首页查询服务，用户选择服务，下单并支付

抢单：服务人员和机构进行抢单。首先服务人员和机构设置接单范围、服务技能、开启抢单开关，然后进入抢单界面进行抢单。

派单调度：平台根据撮合匹配算法通过任务调度将订单和服务人员进行撮合匹配，促进成交。

订单管理：对订单的生命周期进行管理，包括创建订单、取消订单、删除订单、历史订单等。

服务人员管理：对服务人员的信息、认证等进行管理。

企业管理：对机构的信息、认证进行管理。

客户管理：对c端用户的信息、用户的状态等信息进行管理。

营销管理：对优惠券活动进行管理。



## 7. 项目架构

项目是基于Spring Cloud Alibaba框架构建的微服务项目，采用前后端分离模式进行开发，系统架构图如下：

![image](C:\Users\slx99\AppData\Local\Temp\MicrosoftEdgeDownloads\81e6daa9-9e12-4d1b-82ab-8c8947504c5e\image.png)

用户层：

包括四个端：运营端(PC)、服务端（APP）、机构端（PC）、用户端（小程序）

负载层：

反向代理、负载均衡。

服务层：包括网关、业务微服务、基础服务。

业务微服务：包括运营基础服务、客户管理服务、订单管理服务、抢单服务、派单服务、支付服务等。

基础服务：Nacos（服务注册、配置中心）、XXL-JOB（任务调度）、RabbitMQ（消息队列）、Elasticsearch（全文检索）、Canal（数据同步）、Sentinel（熔断降级、限流）等。

数据层：

MySQL数据库存储：服务信息、区域信息、客户信息、订单信息、支付信息、抢单池、派单池、结算信息等。

分库分表：使用ShardingShphere进行分库分表。

TiDB分布式数据库存储：历史订单信息。

消息队列：存储数据同步消息、各类异步消息等。

索引：服务信息、服务提供者信息、订单信息等。

缓存：服务信息、订单信息、服务单信息等。



项目核心交互流程如下图：

![流程图](C:\Users\slx99\AppData\Local\Temp\MicrosoftEdgeDownloads\0f043c85-1e46-4c45-9efd-d3e31d131468\流程图.jpg)



## 8. 工程结构

jzo2o-foundations运营基础服务工程的目录结构如下图：

![image-20240917014828021](C:\Users\slx99\AppData\Roaming\Typora\typora-user-images\image-20240917014828021.png)



## 9. 表结构

![image](C:\Users\slx99\AppData\Local\Temp\MicrosoftEdgeDownloads\4cb340bf-3469-422a-930d-1483dd73cdef\image.png)

**serve_type**：服务类型表

**serve_item**:  服务项表，存储了本平台的家政服务项目

每个服务项都有一个服务类型，一个服务类型下有多个服务项，服务类型与服务项是一对多关系。

**region**：区域表，存储运营地区信息，一般情况区域表行政级别是市。

**serve**:  服务表，存储了各个区域运营的服务及相关信息。

**注意：这里不要把serve表简单理解为只是区域表和服务项表的中间关系表，因为如果是简单的关联关系表只需记录区域表和服务项表各自的****主键****Id即可，serve记录的是平台运营服务的信息，凡是与运营相关的信息都要记录在serve表，比如：运营价格。后期也可能会增加其它运营相关的字段。**

region与serve_item是什么关系？

一个区域下可以设置多个服务项，一个服务项可以被多个区域设置，region与serve_item是多对多关系。
