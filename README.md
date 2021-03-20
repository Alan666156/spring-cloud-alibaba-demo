# spring-cloud-alibaba-demo

踩坑记录：

1、spring-cloud-alibaba-dependencies 2.2.0.RELEASE版本
dubbo2.7.8与springboot的兼容问题
dubbo2.7.8 中新增了MetadataService，会出现本地启动多个dubbo20880端口重复注册，导致端口占用

2、集成seata
确保服务端和客户端seata版本一致
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
    <version>2.2.1.RELEASE</version>
    <exclusions>
        <exclusion>
            <groupId>io.seata</groupId>
            <artifactId>seata-all</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-spring-boot-starter</artifactId>
    <version>1.4.1</version>
</dependency>
1） "no available service found in cluster 'default', please make sure registry config correct and keep your seata server running"
注册不到服务端
解决方案：确认服务端启动正常，配置是否正确

2）can not register RM,err:can not connect to services-server
seata服务端注册的时候默认取的虚拟机的内网ip，导致连接不上
