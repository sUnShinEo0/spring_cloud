package com.lily.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: liling
 * @date: Created in 2020/2/23
 * @description:
 * @version:1.0
 */
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
//        List<ServiceInstance> instances = discoveryClient.getInstances("provider-service");
//        ServiceInstance instanceInfo = instances.get(0);
//        String hostName = instanceInfo.getHost();
//        int port = instanceInfo.getPort();
//        return restTemplate.getForObject("http://" + hostName +":"+ port + "/user/findAll", String.class);
        return restTemplate.getForObject("http://provider-service/user/findAll",String.class);
    }

}
