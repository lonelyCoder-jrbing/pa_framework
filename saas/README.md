     该项目提供封装好的一些service，打成jar包，为业务项目服务，多是作为一些工具类使用，
     或者在相应的pom文件中导入jar包，在相应的项目中引入该maven的坐标
     
     1.mybatis的插件配置，例如sql打印，一级以及二级缓存的开启，数据库存储引擎的选用
     2.redis的redisService，reis分布式锁的实现，reids实现缓切面,只是将原生redis进行封装
     deploye，打包之后在业务模块项目中去使用
     3.elasticSearch的连接客户端,创建一个dubbo服务，并且将dubbo服务注册在nacos上，
     业务模块代码可以在consumer端进行消费
     4.自定义的注解
     5.电子签章
     
     .....