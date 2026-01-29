# sparrow-server-spring-boot-starter

Sparrow Server Spring Book Edition

[中文](./README_zh_CN.md)

Support Function:

- [x] Server Cluster
- [x] [Sparrow Server Dedicated Client](https://github.com/ThierrySquirrel/sparrow-spring-boot-starter)

# Server Cluster:

The Cluster Is Determined By The Client And Can Connect To A Specified Number Of Servers

# Sparrow Server Dedicated Client

[Sparrow Server Dedicated Client,Usually, All Requests Come From Here](https://github.com/ThierrySquirrel/sparrow-spring-boot-starter)

# Message queues and distributed transactions

Ensure that the message can be sent to the server and that the message can be consumed  
Messages can be sent repeatedly for verification, and messages can be consumed repeatedly  
Final consistency  

## Quick Start

```xml
<!--Adding dependencies to pom. XML-->
<dependency>
    <artifactId>sparrow-server-spring-boot-starter</artifactId>
    <groupId>io.github.thierrysquirrel</groupId>
    <version>2.5.0.0-RELEASE</version>
</dependency>
``` 

### configuration file

 ```properties
 ## application.properties
server.port=8080 #端口
spring.h2.console.enabled=true #Open the H2 console
spring.h2.console.path=/h2 #Access path of h2 controller
spring.datasource.url=jdbc:h2:~/testDatasource #H2 database URL
spring.datasource.username=sa #H2 database username
spring.datasource.password=123456 #H2 database password
sparrow.server.url=127.0.0.1:6060 #This is a mandatory field for service startup
 ```

# Start Sparrow Server

 ```java

@SpringBootApplication
public class SparrowServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
 ```
