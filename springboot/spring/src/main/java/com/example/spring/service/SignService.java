package com.example.spring.service;




import com.example.spring.dao.SignDao;
import com.example.spring.entity.Params;
import com.example.spring.entity.Sign;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class SignService {
    @Resource
    private SignDao signDao;


    public PageInfo<Sign> findBySearch(Params params) {
        //开启分页查询
        if (params.getPageNum() == null || params.getPageSize() == null) {
            params.setPageNum(1);
            params.setPageSize(10);
        } else {
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
        }
        //接下来的查询会按照开启的设置来查询
        List<Sign> list = signDao.findBySearch(params);
        return PageInfo.of(list);
    }
    public void add(Sign sign) {
        signDao.insertSelective(sign);
        System.out.println("添加完毕");
    }
    public void update(Sign sign) {
        if ("休假".equals(sign.getStatus())) {
            sign.setStartTime(null);
            sign.setEndTime(null);
        }
        signDao.updateByPrimaryKey(sign);
    }

    public void delete(Integer id) {
        signDao.deleteByPrimaryKey(id);
    }

    public Sign findByUserIdAndDate(Integer userId, String date) {
        return signDao.findByUserIdAndDate(userId, date.replace("-", ""));
    }

    public Sign findUserNameById(Integer id) {
        return signDao.selectByPrimaryKey(id);
    }

    private String calculateAttendanceStatus(Date startTime, Date endTime, Date standardStartTime, Date standardEndTime) {
        if (startTime.compareTo(standardStartTime) <= 0 && endTime.compareTo(standardEndTime) >= 0) {
            return "正常";
        } else if (startTime.compareTo(standardStartTime) > 0) {
            return "迟到";
        } else if (endTime.compareTo(standardEndTime) < 0) {
            return "早退";
        } else {
            return null;
        }
    }


}
