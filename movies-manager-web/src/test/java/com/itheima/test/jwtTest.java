package com.itheima.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwtTest {
    @Test
    public void createToken() {
        //生成JWT--用户登录之后，生成token返回客户端
        //1、准备数据
        Map map = new HashMap();
        map.put("id","100");
        map.put("mobile","138001380000");
        //2、使用jwts工具类生成
        long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .addClaims(map)
                .signWith(SignatureAlgorithm.HS512, "itcast")
                .setExpiration(new Date(now + 300 * 1000))
                .compact();
        //3、打印
        System.out.println(token);
    }
    @Test
    public void parseToken() {
        //解析token
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJtb2JpbGUiOiIxMzgwMDEzODAwMDAiLCJpZCI6IjEwMCIsImV4cCI6MTYzOTY1NzA5Mn0.OJIjGZrCE_ClWfz5VXPM9sJNM0UAZIU8zRzn0qAg2HQi8qn_WBWY5YEmog6ZSDdzjQcSfbJXQmWzlH5W_LTRMQ";
        try {
            //1、调用jwts工具类解析，claims（Map）
            Claims claims = (Claims) Jwts.parser()
                    .setSigningKey("itcast")
                    .parse(token)
                    .getBody();
            //2、获取数据
            Object id = claims.get("id");
            Object mobile = claims.get("mobile");
            System.out.println(id + "--" + mobile);
        }catch (Exception e) {
            //用户未登录
            System.out.println(111);
        }
    }
}
