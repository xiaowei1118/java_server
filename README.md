## java_server

<p align="center">
<a href="http://www.oracle.com/technetwork/java/javase/overview/index.html"><img src="https://img.shields.io/badge/language-java%208.0-orange.svg"></a>
<a href="https://www.jetbrains.com/idea/"><img src="https://img.shields.io/badge/platform-jetbrains-66FF99.svg"></a>
<a href="http://www.eclipse.org/"><img src="https://img.shields.io/badge/platform-eclipse-46aae6.svg"></a>
<a href="http://projects.spring.io/spring-boot/"><img src="https://img.shields.io/badge/SpringBoot-1.5.2-990066.svg"></a>
<a href="http://spring.io/"><img src="https://img.shields.io/badge/spring-4.3.7-3300FF.svg"></a>
<a href="http://www.mybatis.org/mybatis-3/"><img src="https://img.shields.io/badge/mybatis-3.3.0-660000.svg"></a>
<img src="https://img.shields.io/badge/license-MIT%203.0-CC3333.svg">
<img src="https://img.shields.io/badge/release-1.0.0-brightgreen.svg">
</p>

本项目是校园o2o电商项目的服务端和管理平台代码，采用`SpringBoot-Mybatis`架构，集成了丰富的短信，推送，支付，权限管理等功能。

## 更新

- 2017-7-21 
>  
       1. 解决spring jar包过老，跑不起来问题。
       2. 项目更新为springBoot项目，springBoot版本为1.5.2
       3. 支持在intellij下面运行


## 主要功能

|    功能    |   平台                            | 
|------------|-----------------------------------|
|   推送     |   [极光](https://www.jiguang.cn/) |
|   短信     |   [mob](http://www.mob.com/)      |
|   支付     |   [ping++](https://www.pingxx.com/)|
| 接口签名   |   --                               |
| web端数据管理 | [bootstrap-table](http://bootstrap-table.wenzhixin.net.cn/zh-cn/)|
| 权限管理   |   --                              |
| 多校区管理 |   --                              |

## 运行

clone下来的代码支持在eclipse或者intellij下运行，mysql支持，本地支持以SpringBoot的方式启动。

1. 将项目根目录下foryou.sql导入你自己的数据库中。
2. 再application.properties修改数据库连接配置。
3. 执行mvn package 将项目打成war包，在tomcat下运行。
4. 或者更改pom.xml，讲packing方式更改为jar，执行mvn package后在jar包目录下运行```java -jar jar包路径```即可
 ```
   <packaging>jar</packaging>
 ```
5. 本地测试及启动，执行PortraitApplication中的main函数即可启动内置的tomcat，浏览器中访问[localhost:8080/](http://localhost:8080)(端口可以在application.properties里面设置)
即可进入管理端默认的登录页面。
6. 校区管理员账号admin:123456,总校区管理员账号admin_1:123456。
 
## License

本项目基于[MIT](https://www.opensource.org/licenses/mit-license.php)开源协议. 
