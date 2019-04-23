package com.tensquare.manager.filter;

import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: com.tensquare.manager.filter
 * @version: 1.0
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了过滤器");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }

        if (request.getRequestURL().indexOf("/admin/login") > 0) {
            System.out.println("登录页面:" + request.getRequestURL());
            return null;
        }

        String authHeader = request.getHeader("Authorization");

        String prefix = "Bearer ";

        if (StringUtils.isNotEmpty(authHeader) && authHeader.startsWith(prefix)) {

            try {
                String token = authHeader.substring(prefix.length());

                Claims claims = jwtUtil.parseJWT(token);

                String roles = (String) claims.get("roles");
                if (StringUtils.isNotEmpty(roles) && "admin".equals(roles)) {
                    currentContext.addZuulRequestHeader("Authorization", authHeader);
                    System.out.println("token通过验证,添加头信息:" + authHeader);
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                currentContext.setSendZuulResponse(false);
            }

        }
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(403);
        currentContext.getResponse().setContentType("text/html;charset=UTF-8");
        currentContext.setResponseBody("无权访问");

        return null;
    }
}
