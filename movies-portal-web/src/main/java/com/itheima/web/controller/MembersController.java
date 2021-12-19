package com.itheima.web.controller;

import com.itheima.domain.Members;
import com.itheima.domain.User;
import com.itheima.service.MembersService;
import com.itheima.service.UserService;
import com.itheima.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MembersController {
    @Autowired
    private MembersService membersService;
    @RequestMapping("/menbers/login")
    public String login(String username,String password){
        Members members=membersService.login(username,password);
        if(members==null){
            return "";
        }
        HashMap map = new HashMap();
        map.put("id",members.getId());
        map.put("username",members.getUsername());
        map.put("password",members.getPassword());
        String token = JwtUtil.createToken(map);
        return token;
    }
}
