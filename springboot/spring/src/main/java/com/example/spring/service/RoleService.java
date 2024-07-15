package com.example.spring.service;

import com.example.spring.dao.RoleDao;
import com.example.spring.entity.Role;
import com.example.spring.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;

    public void add(Role role) {
        roleDao.insertSelective(role);
    }

    public void delete(Integer id) {
        roleDao.deleteByPrimaryKey(id);
    }

    public PageInfo<Role> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会按照开启的设置来查询
        List<Role> list = roleDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(Role role) {
        System.out.println("开始执行更新操作");
        roleDao.updateByPrimaryKeySelective(role);
        System.out.println("执行更新操作完毕");
    }
}
