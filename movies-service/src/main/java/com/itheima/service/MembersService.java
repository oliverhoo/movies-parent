package com.itheima.service;

import com.itheima.domain.Members;
import com.itheima.domain.User;

public interface MembersService {
     Members login(String username, String password);
}
