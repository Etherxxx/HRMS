package com.example.spring.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.JwtTokenUtils;
import com.example.spring.dao.NoticeDao;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Notice;
import com.example.spring.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Resource
    private NoticeDao noticeDao;

    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        //获取操作人信息
        String username = "";
        Admin user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNotNull(user)) {
            assert user != null;
            username = user.getName();
        }
        notice.setUsername(username);
        noticeDao.insertSelective(notice);
    }

    public PageInfo<Notice> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会按照开启的设置来查询
        List<Notice> list = noticeDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeDao.updateByPrimaryKeySelective(notice);
    }

    public void delete(Integer id) {
        noticeDao.deleteByPrimaryKey(id);
    }

    public List<Notice> findNewNotice() {
        return noticeDao.findNew3();
    }
}
