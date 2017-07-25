### java_server
本项目是基于`SpringMVC+spring+Mybatis`的校园o2o电商项目的后台和管理平台。

#### 更新
- 2017-7-21 
>  
       1. 解决spring jar包过老，跑不起来问题。
       2. 项目更新为springBoot项目，springBoot版本为1.5.2
       3. 支持在intellij下面运行


#### 主要功能
- jpush推送
- mob短信
- ping++支付
- 接口签名
- web端数据管理
- 权限管理
- 多校区管理

#### 运行
clone下来的代码支持在eclipse或者intellij下运行，mysql支持，本地支持以springBoot的方式启动。

1. 将foryou.sql导入你自己的数据库中
2. 修改数据库连接配置
3. 执行`mvn package` 将项目达成war包，在tomcat下运行。
4. 或者更改pom.xml，讲packing方式更改为jar，执行mvn package后在jar包目录下运行```java -jar jar包路径```即可
 ```
   <packaging>jar</packaging>
 ```
 
 ## License
 java_server is available under the [MIT](https://www.opensource.org/licenses/mit-license.php) license. See the LICENSE file for more info.
