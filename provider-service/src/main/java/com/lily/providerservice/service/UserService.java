package com.lily.providerservice.service;

import com.lily.providerservice.pojo.User;

import java.util.List;

/**
 * @author: liling
 * @date: Created in 2020/2/23
 * @description:
 * @version:1.0
 */
public interface UserService {

    List<User> findAll();

}
