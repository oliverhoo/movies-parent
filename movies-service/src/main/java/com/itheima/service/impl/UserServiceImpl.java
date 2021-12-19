package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.domain.UserExample;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findByPhone(String telephone) {
        //System.out.println(telephone);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        //查询号码
        criteria.andTelephoneEqualTo(telephone);
        List<User> userList = userDao.selectByExample(userExample);
        if(userList!=null&&!userList.isEmpty()){
            return userList.get(0);
        }else {
            return null;
        }
    }
}
