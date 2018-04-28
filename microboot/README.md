# microboot 是什么，能做什么？
### microboot是什么
microboot 是基于Netty 开发的一个Http服务框架，自身提供http server功能，jar包方式启动，使用方式与架构与SpringMVC极其相似，microboot没有遵循java web规范，非常轻量级，并且高性能。
### microboot能做什么
microboot天生是为了后端纯接口服务做得框架，具有开发、部署简单，高性能并且稳定，支持所有http请求方式，支持多视图，并且可以根据自己的要求自定义ViewResolver返回自己想要的结果，目前服务只String、JSONView 两种返回。

# microboot 优缺点
### 优点
- 支持HTTP2.0
- 超级轻量级的框架
- 性能优越，稳定，尤其对于非常暴增的流量，能够稳定支撑
- 使用简单，上手快，只要学过springMVC 基本没有任何学习瓶颈
- 非常干净，不占用多余端口，非特殊情况不需要对他的性能调优
- 开发简单、启动快，jar包启动
- 支持一机多应用，节省开发、测试服务器资源
- 许多后端接口使用的特性支持，如多接口合并
- 等等
### 缺点
- 由于不支持java web规范，所以不能支持jsp（绝对不会支持），目前还不支持静态资源(作者还没写，以后会支持)，freemark等模板语言不支持(作者还没有写，可以自己处理直接返回String)
- 不是标准，非权威

# microboot设计思想
microboot设计思想源于springMVC，摒弃J2EE繁杂的规范，更加纯粹的进行HTTP编程，不依赖于任何第三方容器，运用高性能的Netty框架做server ，以及netty的线程模型来处理业务。一旦你使用了microboot，或许你再也不想用tomcat做你的容器了。
    TIPS:下图是增加HTTP2.0功能之前的架构，HttpSimpleChannelHandler变更为多个Handler，主要是为了支持Http2.0，后面架构不变
- ![image](https://github.com/wwjwell/microboot/raw/master/docs/microboot.png)

# microboot依赖情况
microboot强依赖于netty 、jackson、slf4j、spring，需要你在项目中引入这4个jar

# microboot怎么使用
### maven配置
```
    <!-- 依赖 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
    </dependency>
    <dependency>
        <groupId>io.netty</groupId>
        <artifactId>netty-all</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
   </dependency>
   <dependency>
       <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
   </dependency>
```
```
    <dependency>
      <groupId>com.github.wwjwell</groupId>
      <artifactId>microboot</artifactId>
      <version>2.0.0</version>
    </dependency>
```
### spring 配置 
* 可参照 microboot-demo/src/main/resources/api.xml
``` 
    <context:component-scan base-package="com.dangry.microboot.demo.**">
        <!-- 扫描ApiCommand注解 -->
        <context:include-filter type="annotation" expression="com.dangry.microboot.mvc.annotation.ApiCommand"/>
    </context:component-scan>
    <!--追求高性能，需要单独设置适合自己业务逻辑的线程池-->
    <bean id="threadPoolTaskExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
        <property name="corePoolSize" value="8" />
        <property name="threadNamePrefix" value="microboot-task-"/>
        <property name="waitForTasksToCompleteOnShutdown" value="true" />
    </bean>
    <bean id="serverConfig" class="com.dangry.microboot.ServerConfig">
        <property name="port" value="8080"/>
        <property name="maxLength" value="65536"/> <!-- 传输的报文过大，会报错 -->
        <property name="maxKeepAliveRequests" value="1000"/>  <!-- keep-alive 最大请求数 -->
        <property name="executor" ref="threadPoolTaskExecutor"/> <!--追求高性能，需要单独设置适合自己业务逻辑的线程池-->
        <!-- 打开调试开关 ，生产环境请关闭 -->
        <property name="openConnectCostLogger" value="true"/>
        <property name="openMetricsLogger" value="true"/>
    </bean>
    <!-- config server -->
    <bean name="server" class="com.dangry.microboot.Server">
        <property name="serverConfig" ref="serverConfig"/> <!-- set port=8080 -->
    </bean>
```
### SSL 默认开启HTTP2.0
```
       <bean id="serverConfig" class="com.dangry.microboot.ServerConfig">
        <property name="port" value="8080"/>
        <property name="maxLength" value="65536"/> <!-- 传输的报文过大，会报错 -->
        <property name="maxKeepAliveRequests" value="1000"/>  <!-- keep-alive 最大请求数 -->
        <property name="executor" ref="threadPoolTaskExecutor"/> <!--追求高性能，需要单独设置适合自己业务逻辑的线程池-->
        <!-- 打开调试开关 ，生产环境请关闭 -->
        <property name="openConnectCostLogger" value="true"/>
        <property name="openMetricsLogger" value="true"/>

        <!-- 配置SSL 默认支持http2.0 这4项 -->
        <property name="openSSL" value="true"/>
        <!-- 证书 -->
        <property name="keyCertChainFilePath" value="/Users/wwj/work/server.crt"/>
        <!-- 私钥pem格式 -->
        <property name="keyFilePath" value="/Users/wwj/work/server_key.pem"/>
        <!-- 私钥密码 -->
        <property name="keyPassword" value="123456"/>
    </bean>

```

### 输入helloworld
```
    @ApiCommand
    public class HelloWorldCommand{
        @ApiMethod("/hello/world")
        public String helloWorld(){
            return "Hello World";
        }    
    }
    
    curl http://localhost:8080/hello/world   
    #输出 Hello World
```
### 输出Json 
```
    @ApiCommand("json")
    public class JsonCommand{
        @ApiMethod("/test",httpMethod = ApiMethod.HttpMethod.POS)
        public ModelAndView test(@ApiParam("name")String name,@ApiParam("id")int id){
            ModelAndView mv = new ModelAndView("jsonView");
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("id",id);
            res.put("name", name);
            mv.setResult(mv);
            return mv;
        }    
    }
    
    curl -d 'name=tomcat&id=100' 'http://localhost:8080/json/test'   
    #输出 {"name":"tomcat","id":100}
```
### 更多例子和用法
请看microboot-demo

# 拦截器
* 类springMVC拦截器
  增加拦截器，需要在spring配置文件中显示调用

```
  <bean class="com.dangry.microboot.demo.interceptor.TestInterceptor">  
      <property name="order" value="1"/>
  </bean>
    
    public class TestInterceptor extends AbstractApiInterceptor {
        int order ; //拦截器执行顺序
        //分发之前，甚至可以更改最终invoke的接口
        @Override
        public boolean preDispatch(HttpContextRequest request, HttpContextResponse response) {
            System.out.println("TestInterceptor preDispatch url="+request.getRequestUrl());
            return super.preDispatch(request, response);
        }
        
        //执行invoke方法之前，可以更改，增加删除参数值
        @Override
        public boolean postHandler(ApiCommandMapping mapping, HttpContextRequest request, HttpContextResponse response) {
            System.out.println("TestInterceptor postHandler url="+request.getRequestUrl());
            return super.postHandler(mapping, request, response);
        }
        
        //执行invoke方法之后，可以修改返回值
        @Override
        public void afterHandle(ApiCommandMapping mapping, Object modelView, HttpContextRequest request, HttpContextResponse response, Throwable throwable) {
            System.out.println("TestInterceptor afterHandle url="+request.getRequestUrl());
            super.afterHandle(mapping, modelView, request, response, throwable);
        }
    }
```

# 接口合并
    暴露内部doProcess方法，可以操控任意一个或多个ApiMethod的返回。可以比较轻易的获取改方法返回，通常用于接口合并，减少请求交互次数
    BatchCommandTest例子：APP需要请求/t1,/t2 接口，可以直接请求/batch，batch根据约定可以组装t1,t2的返回值
* 设置spring配置文件 需要增加一个设置，获取apiDispatcher内部处理类，调用doProcess方法
```
    <bean class="com.dangry.microboot.mvc.ApiDispatcher"/>
```

具体用法参考microboot-demo的 com.dangry.microboot.demo.command.BatchCommndTest类
