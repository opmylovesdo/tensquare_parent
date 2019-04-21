package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/21
 * @Description: com.tensquare.user.interceptor
 * @version: 1.0
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        System.out.println("经过了拦截器");
        String authorization = request.getHeader("Authorization");
        String prefix = "Bearer ";
        if (StringUtils.isNotEmpty(authorization)) {
            if (authorization.startsWith(prefix)) {

                String token = authorization.substring(prefix.length());
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");

                    if(null != roles && "admin".equals(roles)){
                        request.setAttribute("claims_admin", claims);
                    }

                    if(null != roles && "user".equals(roles)){
                        request.setAttribute("claims_user", claims);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("token错误");
                }
            }
        }
        return true;
    }
}
