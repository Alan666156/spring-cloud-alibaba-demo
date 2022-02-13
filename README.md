# spring-cloud-alibaba-demo 集成seata分布式事务

踩坑记录：

1、spring-cloud-alibaba-dependencies 2.2.5.RELEASE版本
dubbo2.7.8与springboot的兼容问题
dubbo2.7.8 中新增了MetadataService，会出现本地启动多个dubbo20880端口重复注册，导致端口占用

2、集成seata 确保服务端和客户端seata版本一致，1.4.2支持在nacos中直接配置，客户端直接配置nacos dataId
启动命令：./bin/seata-server.sh -h 101.133.234.7 -p 8091
    <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
      <version>2.2.5.RELEASE</version>
      <exclusions>
        <exclusion>
          <groupId>io.seata</groupId>
          <artifactId>seata-all</artifactId>
        </exclusion>
        <exclusion>
          <groupId>io.seata</groupId>
          <artifactId>seata-spring-boot-starter</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
    <dependency>
      <groupId>io.seata</groupId>
      <artifactId>seata-spring-boot-starter</artifactId>
      <version>1.4.2</version>
    </dependency>
1） "no available service found in cluster 'default', please make sure registry config correct and keep your seata server running"
注册不到服务端
解决方案：确认服务端启动正常，配置是否正确

2）can not register RM,err:can not connect to services-server
seata服务端注册的时候默认取的虚拟机的内网ip，导致连接不上

3、elasticsearch服务器版本要和本地服务版本保持一致，否则会因为版本冲突导致应用服务无法正常启动，这里使用的6.8.7版本，根据springcloud版本选择elaticsearch

4、sentinel接入需要保证服务双方网络互通 localhost:8080
    <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
      <version>2.2.5.RELEASE</version>
    </dependency>
    <!-- sentinel数据源 -->
    <dependency>
      <groupId>com.alibaba.csp</groupId>
      <artifactId>sentinel-datasource-nacos</artifactId>
      <version>1.8.1</version>
    </dependency>
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar

5、微服务调用链路日志,这里使用Twitter开源的Zipkin
java -jar zipkin-server-2.12.9-exec.jar
Trace是指一次请求调用的链路过程，trace id 是指这次请求调用的ID；在一次请求中，会在网络的最开始生成一个全局唯一的用于标识此次请求的trace id，这个trace id在这次请求调用过程中无论经过多少个节点都会保持不变，并且在随着每一层的调用不停的传递
Span是指一个模块的调用过程，一般用span id来标识。在一次请求的过程中会调用不同的节点/模块/服务，每一次调用都会生成一个新的span id来记录
Annotation是指附属信息，可以用于附属在每一个Span上自定义的数据。

    <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-sleuth</artifactId>
       </dependency>
     <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-sleuth-zipkin</artifactId>
     </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>