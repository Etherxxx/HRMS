package com.example.spring.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spring.entity.Admin;
import com.example.spring.service.AdminService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenUtils {
    private static AdminService staticAdminService;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private AdminService adminService;

    @Autowired
    public JwtTokenUtils(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
    }

    //生成token
    public static String genToken(String userId, String password) {
        return JWT.create().withAudience(userId)//将userId保存到token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 1))//1小时后token凭证过期
                .sign(Algorithm.HMAC256(password));//将password作为token密钥

    }

    //获取当前登录用户信息
    public static Admin getCurrentUser(){
        String token=null;
        try{
            HttpServletRequest request=((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            token=request.getHeader("token");
            if(StrUtil.isBlank(token)){
                token=request.getParameter("token");
            }
            if(StrUtil.isBlank(token)){
                log.error("获取当前登录token失败,token:{}",token);
                return null;
            }
            String userId = JWT.decode(token).getAudience().get(0);
            Admin admin = staticAdminService.findById(Integer.valueOf(userId));
            return admin;
        }catch (Exception e){
            log.error("获取当前用户信息失败,token={}",token,e);
            return null;
        }
    }
}
