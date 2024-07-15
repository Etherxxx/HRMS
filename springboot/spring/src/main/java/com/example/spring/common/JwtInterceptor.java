package com.example.spring.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.spring.entity.Admin;
import com.example.spring.exception.CustomException;
import com.example.spring.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


//JWT拦截器
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log= LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) {
        //1.从http请求中获取token
        String token=request.getHeader("token");
        if(StrUtil.isBlank(token)){
            //若在header中为空，则去参数中请求token
            token=request.getParameter("token");
        }
        //2.开始执行认证
        if(StrUtil.isBlank(token)){
            throw new CustomException("获取token失败，请重新登录");
        }
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        //获取token中的用户id
        String userId;
        Admin admin;
        try{
            userId= JWT.decode(token).getAudience().get(0);
            //根据token中的userid查询数据库
            admin=adminService.findById(Integer.parseInt(userId));
        }catch (Exception e){
            String errMsg="token验证失败，请重新登录";
            log.error(errMsg+",token="+token,e);
            throw new CustomException(errMsg);
        }
        if(admin==null){
            throw new CustomException("用户不存在，请重新登录");
        }
        //用户密码加签验证token
        try{
            JWTVerifier jwtVerifier= JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new CustomException("token验证失败，请重新登陆");
        }
        return true;
    }
}
