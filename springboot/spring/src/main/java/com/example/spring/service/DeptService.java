package com.example.spring.service;

import com.example.spring.dao.DeptDao;
import com.example.spring.entity.Dept;
import com.example.spring.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {
    @Resource
    private DeptDao deptDao;

    public void add(Dept dept) {
        deptDao.insertSelective(dept);
    }

    public void delete(Integer id) {
        deptDao.deleteByPrimaryKey(id);
    }

    public PageInfo<Dept> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会按照开启的设置来查询
        List<Dept> list = deptDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(Dept dept) {
        deptDao.updateByPrimaryKeySelective(dept);
    }

    public Dept findById(Integer id) {
        return deptDao.selectByPrimaryKey(id);
    }

    public String findDeptNameById(Integer deptId) {
        return deptDao.findDeptNameById(deptId);
    }
}
