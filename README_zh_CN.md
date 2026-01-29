# sparrow-server-spring-boot-starter

Sparrow Server Spring Book Edition

[English](./README.md)

支持功能:

- [x] 服务器集群
- [x] [Sparrow Server专用客户端](https://github.com/ThierrySquirrel/sparrow-spring-boot-starter)

# 服务器集群:

集群由客户端决定,可以连接指定数量的服务器

# Sparrow Server专用客户端

[Sparrow专用客户端,通常,所有请求都来自这里](https://github.com/ThierrySquirrel/sparrow-spring-boot-starter)

# 消息队列与分布式事务

保证消息一定能够发送到服务端,保证消息一定能够被消费  
消息可重复发送验证,消息可重复消费  
最终一致性  

## Quick Start

```xml
<!--在pom.xml中添加依赖-->
<dependency>
    <artifactId>sparrow-server-spring-boot-starter</artifactId>
    <groupId>io.github.thierrysquirrel</groupId>
    <version>2.5.0.0-RELEASE</version>
</dependency>
``` 

### 配置文件

 ```properties
 ## application.properties
server.port=8080 #端口
spring.h2.console.enabled=true #开启h2控制台
spring.h2.console.path=/h2 #h2控制器的访问路径
spring.datasource.url=jdbc:h2:~/testDatasource #H2数据库Url
spring.datasource.username=sa #H2数据库username
spring.datasource.password=123456 #H2数据库password
sparrow.server.url=127.0.0.1:6060 # 这是必须填写的，用于服务启动
 ```

# 启动Sparrow Server

 ```java

@SpringBootApplication
public class SparrowServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
 ```
 

 
