    该项目为业务项目提供服务
    包括登录，动态路由，mq消息队列，dubbo的日志过滤等等
    1.登录系统
        1>对logbean的加密
        2>将对logbean加密完成的密文存储在redis中去
    2.nacos的动态路由。结合springCloud gateWay,在项目启动之后，可以在nacos端动态的添加或者删除路由信息
      避免停机
      具体的作用有：
      api网关封装了系统的架构，为每一个客户端提供一个定制的api。它还具有其他的职责，如身份验证，监控
      负载均衡，缓存，请求分片与管理，静态响应和处理。
      api网关的特点是，所有的客户端和消费端都是通过api网关接入微服务，在网关层处理所有的非业务功能。
      
    3.rabbitmq 提供消息队列的服务
    4.flink.flink提供实时计算的功能，在paas上启动器服务，数据通过kafka或者rabbit发送过来进行实时计算，
    将计算完成的数据同样通过kafka或者nacos的方式发送到业务模块
    
    
    
    paas-api 为外部系统提供dubbo或者其他的api服务
    
    paas-jps 负责数据库的持久化
    
    paas-service 是paas-web中的业务层实现
    
    paas-web 提供一些web服务例如分布式系统的登录等
    