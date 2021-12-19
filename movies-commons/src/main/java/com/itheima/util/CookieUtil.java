package com.itheima.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    //向cookie中写入token
    public static void setTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("Authentication", token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    //从cookie中获取token
    public static String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authentication")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}
