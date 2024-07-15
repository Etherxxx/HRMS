package com.example.spring.service;

import com.example.spring.dao.LogDao;
import com.example.spring.entity.Log;
import com.example.spring.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    @Resource
    private LogDao logDao;

    public void add(Log log) {
        logDao.insertSelective(log);
    }

    public void delete(Integer id) {
        logDao.deleteByPrimaryKey(id);
    }

    public PageInfo<Log> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会按照开启的设置来查询
        List<Log> list = logDao.findBySearch(params);
        return PageInfo.of(list);
    }

}
