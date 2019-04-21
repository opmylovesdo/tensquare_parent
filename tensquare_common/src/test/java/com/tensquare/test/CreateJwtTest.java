package com.tensquare.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/21
 * @Description: com.tensquare.test
 * @version: 1.0
 */
public class CreateJwtTest {

    @Test
    public void createJwtTest() {
        JwtBuilder builder = Jwts.builder()
                .setId("888") //用户id
                .setSubject("洛洛") //用户名
                .setIssuedAt(new Date()) //登录时间
                .signWith(SignatureAlgorithm.HS256, "luoluo")
                .setExpiration(new Date(new Date().getTime() + 600000L))
                .claim("role", "admin");

        System.out.println(builder.compact());
    }

    @Test
    public void parseJwtTest(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLmtJvmtJsiLCJpYXQiOjE1NTU4MjgzNjksImV4cCI6MTU1NTgyODk2OSwicm9sZSI6ImFkbWluIn0.dkb_LGdOQvcRlkiH4hj02SgpAieuw_Ie-C-MFQk-MMI";

        Claims claims = Jwts.parser().setSigningKey("luoluo")
                .parseClaimsJws(token).getBody();

        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getId());
        System.out.println("IssuedAt:" + claims.getIssuedAt());
        System.out.println("Expiration:" + claims.getExpiration());
        System.out.println("role:" + claims.get("role"));


    }
}
