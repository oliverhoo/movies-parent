package com.itheima.web.controller;

import cn.hutool.core.util.StrUtil;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.util.JwtUtil;
import com.itheima.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserService userService;
    @GetMapping("/user/sendMessage")
    public Boolean sendMessage(String telephone){
        try {
            System.out.println(telephone);
            User user=userService.findByPhone(telephone);
            if(user==null){
                return false;
            }
            System.out.println("11");
            String code = "123456";
            //SmsUtil.sendSms(telephone,code);
            redisTemplate.opsForValue().set("loginSmsCode_"+telephone, code , 5 , TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @GetMapping("/user/login")
    public String userLogin(String telephone,String code){
        //System.out.println(telephone+","+code);
        String redisCode = redisTemplate.opsForValue().get("loginSmsCode_" + telephone);
        if(!StrUtil.equals(redisCode,code)){
            return "";
        }
        //删除缓存
        redisTemplate.delete(("loginSmsCode_" + telephone));
        User user = userService.findByPhone(telephone);
        if(user==null){
            return "";
        }else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("user_id",user.getId());
            map.put("user_telephone",user.getTelephone());
            map.put("user_code",user.getPassword());
            String token = JwtUtil.createToken(map);
            //存入redis
            redisTemplate.opsForValue().set(token  , "1" , 30 ,TimeUnit.MINUTES );
            return token;
        }
    }
    @GetMapping("/user/verify")
    public String checkLoginStatus(@RequestHeader("Authorization") String token){
        System.out.println("校验权限:" + token);
        //获得redis中的数据  redisValue = 1
        String redisValue = redisTemplate.opsForValue().get(token);
        Boolean flag = redisTemplate.hasKey(token);
        if( !flag ){
            return "401" ;
            //权限不足  服务器缓存过期了 或者 根本就没有存储这个token
        }

        // 续期操作 - 每个操作 应该重新将redis的时间修改成30分钟  ,时间重新计算
        // 用户停止操作的一瞬间 开始往后顺延三十分钟不操作 才会自动消失token令牌
        redisTemplate.opsForValue().set(token  , "1" , 30 ,TimeUnit.MINUTES );
        return "200"; //请求成功
    }
}
