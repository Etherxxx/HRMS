package com.example.spring.dao;


import com.example.spring.entity.Dept;
import com.example.spring.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DeptDao extends Mapper<Dept> {
    List<Dept> findBySearch(@Param("params") Params params);

    @Select("select name from dept where id=#{deptId}")
    String findDeptNameById(Integer deptId);
}
