package com.lily.providerservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lily.providerservice.pojo.User;
import com.lily.providerservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liling
 * @date: Created in 2020/2/23
 * @description:
 * @version:1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    private String findAll() throws JsonProcessingException {
        List<User> all = userService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(all);
    }

}
