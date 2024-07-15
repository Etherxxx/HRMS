package com.example.spring.service;


import com.example.spring.dao.SalaryDao;
import com.example.spring.entity.Params;
import com.example.spring.entity.Salary;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {
    @Resource
    private SalaryDao salaryDao;

    public PageInfo<Salary> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会按照开启的设置来查询
        List<Salary> list = salaryDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Salary salary) {
        addSalary(salary);
    }

    public void update(Salary salary) {
        updateSalary(salary);
    }

    public void delete(Integer id) {
        salaryDao.deleteByPrimaryKey(id);
    }

    private void addSalary(Salary salary) {
        // 计算总值
        float total = salary.getBasic() + salary.getBonus()+salary.getAllowance()-salary.getInsurence()-salary.getFine();
        // 设置总值
        salary.setTotal(total);
        // 保存薪资记录到数据库
        salaryDao.insertSelective(salary);

    }

    private void updateSalary(Salary salary) {
        // 计算总值
        float total = salary.getBasic() + salary.getBonus()+salary.getAllowance()-salary.getInsurence()+salary.getFine();
        // 设置总值
        salary.setTotal(total);
        // 保存薪资记录到数据库
        salaryDao.updateByPrimaryKeySelective(salary);
    }

}
