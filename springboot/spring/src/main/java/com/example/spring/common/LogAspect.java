package com.example.spring.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Log;
import com.example.spring.service.LogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
@Aspect
public class LogAspect {
    @Resource
    private LogService logService;

    @Around("@annotation(autoLog)")
    public Object doAround(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {
        //操作内容
        String name = autoLog.value();
        //操作时间
        String time = DateUtil.now();

        //获取操作ip地址
        HttpServletRequest request=((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip =request.getRemoteAddr();

        //获取操作人信息
        String username = "";
        Admin user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNotNull(user)) {
            assert user != null;
            username = user.getName();
        }

        Result result = (Result) joinPoint.proceed();
        if (ObjectUtil.isNotNull(result.getData())) {
            if (result.getData() instanceof Admin) {
                username = ((Admin) result.getData()).getName();
            }
        }

        //将操作日志添加到数据库
        Log log = new Log(null,name, time, username, ip);
        logService.add(log);
        return result;
    }
}
