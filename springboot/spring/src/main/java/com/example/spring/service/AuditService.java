package com.example.spring.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.AutoLog;
import com.example.spring.common.JwtTokenUtils;
import com.example.spring.dao.AdminDao;
import com.example.spring.dao.AuditDao;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Audit;
import com.example.spring.entity.Params;
import com.example.spring.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
    @Resource
    private AuditDao auditDao;
    @Resource
    private AdminDao adminDao;

    public void add(Audit audit) {
        auditDao.insertSelective(audit);
    }

    public void update(Audit audit) {
        auditDao.updateByPrimaryKeySelective(audit);
    }

    public void delete(Integer id) {
        auditDao.deleteByPrimaryKey(id);
    }

    public PageInfo<Audit> findBySearch(Params params) {
        Admin user =JwtTokenUtils.getCurrentUser();
        if(ObjectUtil.isNull(user)){
            throw new CustomException("未从token中获取到用户信息，请重新登陆");
        }
        //如果当前用户是员工，则只查询该员工的请假记录
        if("ROLE_EMPLOYEE".equals(user.getRole())){
            params.setUserId(user.getId());
        }
        //开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会按照开启的设置来查询
        List<Audit> list = auditDao.findBySearch(params);
//        for (Audit audit : list) {
//            if (ObjectUtil.isNotEmpty(audit.getUserId())) {
//                Admin user1 = adminDao.selectByPrimaryKey(audit.getUserId());
//                if (ObjectUtil.isNotEmpty(user1)) {
//                    audit.setUserName(user1.getName());
//                }
//            }
//        }
        return PageInfo.of(list);
    }

}
