package com.itheima.service.impl;

import com.itheima.dao.MembersDao;
import com.itheima.domain.Members;
import com.itheima.domain.MembersExample;
import com.itheima.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembersServiceImpl implements MembersService {

    @Autowired
    private MembersDao membersDao;
    @Override
    public Members login(String username, String password) {
        MembersExample membersExample = new MembersExample();
        MembersExample.Criteria criteria = membersExample.createCriteria();
        criteria.andUsernameEqualTo( username );
        criteria.andPasswordEqualTo( password );
        //查询数据
        List<Members> membersList = membersDao.selectByExample(membersExample);
        //membersList.size() 有性能问题 计算集合的总量 再执行判断
        //isEmpty() 只是判断是不是空集合 效率比size高
        if(membersList!=null && !membersList.isEmpty()){
            return membersList.get(0);
        }
        return null;
    }
}
