package com.bishugui.project.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

//@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {
    //签名私钥
    private String key;
    //签名失效时间
    private Long failureTime;

    /**
     * 设置认证token
     *
     * @param id      用户登录ID
     * @param subject 用户登录名
     * @param map     其他私有数据
     * @return
     */
    public String createJwt(String id, String subject, Map<String, Object> map) {

        //1、设置失效时间啊
        long now = System.currentTimeMillis();  //毫秒
        long exp = now + failureTime;

        //2、创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(subject)
                .setIssuedAt(new Date())
                //设置签名防止篡改
                .signWith(SignatureAlgorithm.HS256, key);

        //3、根据map设置claims
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));

        //4、创建token
        String token = jwtBuilder.compact();
        return token;
    }

    public String createJwt(String id, String subject) {

        //1、设置失效时间啊
        long now = System.currentTimeMillis();  //毫秒
        long exp = now + failureTime;
        //byte[] decode = TextCodec.BASE64.decode(key);
        //2、创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(subject)
                .setIssuedAt(new Date())
                //设置签名防止篡改
                .signWith(SignatureAlgorithm.HS256, key);
        //3、创建token
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public Claims parseJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Long failureTime) {
        this.failureTime = failureTime;
    }
}


