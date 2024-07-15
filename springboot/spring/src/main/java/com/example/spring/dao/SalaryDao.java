package com.example.spring.dao;

import com.example.spring.entity.Params;
import com.example.spring.entity.Salary;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SalaryDao extends Mapper<Salary> {
    List<Salary> findBySearch(@Param("params") Params params);
}
