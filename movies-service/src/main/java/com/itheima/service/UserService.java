package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {
    public User findByPhone(String telephone);
}
