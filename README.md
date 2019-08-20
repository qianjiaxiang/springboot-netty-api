# springboot-netty-api
springboot整合netty实现api接口，目前提供了GET、POST以及文件上传接口
# 项目结构
- com.api.netty.demo
- --------------bean
-     -------------------dto
-     -------------------vo
-     --------------config
-     -------------------commons
-     -------------------handler
-     -------------------http
-     -------------------router
-     --------------controller
-     -------------------test
-     -------------------test1
-     --------------mapper
-     --------------service
-     --------------util
    
# 功能概述
- config包，主要定义了常用的配置类、以及netty的handler 还有请求转发路由
- controller包：接口的实现

#DemoApplication
- 启动的时候需要将netty启动项添加至springboot启动，并且配置好需要扫描路径的controller包

    
