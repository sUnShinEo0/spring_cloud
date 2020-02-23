# spring_cloud
1. 创建mave父工程

2. 创建spring-boot子工程, privider-service提供微服务

3. 创建spring-boot子工程, consumer-service消费者来调用服务

4. 创建spring-boot子工程, 勾选Eureka的starter, 创建服务中心
    4.1 配置文件: application.properties
```
#配置端口号
server.port=10086
#配置server的应用名称
spring.application.name=eureka-server
#配置服务注册中心地址
eureka.client.service-url.defaultZone=http://localhost:10086/eureka


#设置eureka不像自己注册自己
# 是否抓取注册列表
# 是否注册服务中心Eureka eureka:
eureka.client.fetch-registry=false
eureka.client.register-with-eureka=false

```
5. 改造provider-service, 引入eureka-client依赖, 将service注册到Eureka中
   5.1 pom.xml引入依赖
  ```
      <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
  5.2 启动类application使用 `@EnableDiscoveryClient`注解,是Eureka可以发现
  5.3 配置application.properties文件
  ```
#配置app的名字
spring.application.name=provider-service
#配置注册中心地址,此处的地址要和eureka中声明的一致
eureka.client.service-url.defaultZone=http://localhost:10086/eureka
```

6.  改造consumer-service类, 步骤同上
7. 使用消费者访问提供者
```
@RestController
@RequestMapping("/user")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    //注意这里的导包不要搞错
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/findAll")
    private String findAll() {
  //通过应用名获得服务实例
   List<ServiceInstance> instances = discoveryClient.getInstances("provider-service");
        ServiceInstance instanceInfo = instances.get(0);
//获取服务的主机地址
        String hostName = instanceInfo.getHost();
//获得端口号
        int port = instanceInfo.getPort();
//使用restTemplate访问
        return restTemplate.getForObject("http://" + hostName +":"+ port + "/user/findAll", String.class);
   }

}
```



_________________________________________

__________________________________________
关于负载均衡:
Eureka集成了Ribbon负载均衡器, 无需再引入依赖
1. 开启负载均衡器,    `@LoadBalanced`, 默认方式是轮询策略
```
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced  //开启负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```
2. 修改访问接口的方式
```
    @RequestMapping("/findAll")
    private String findAll() {
//        List<ServiceInstance> instances = discoveryClient.getInstances("provider-service");
//        ServiceInstance instanceInfo = instances.get(0);
//        String hostName = instanceInfo.getHost();
//        int port = instanceInfo.getPort();
//        return restTemplate.getForObject("http://" + hostName +":"+ port + "/user/findAll", String.class);

      //这里地址直接使用微服务的应用名, 不需要host和port
        return restTemplate.getForObject("http://provider-service/user/findAll",String.class);
    }

```
