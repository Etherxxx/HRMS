package com.example.spring.dao;

import com.example.spring.entity.Admin;
import com.example.spring.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminDao extends Mapper<Admin> {
    List<Admin> findBySearch(@Param("params") Params params);

    @Select("select * from user where name = #{name} limit 1")
    Admin findByName(@Param("name") String name);

    @Select("select * from user where name = #{name} and password=#{password} limit 1")
    Admin findByNameAndPassword(@Param("name") String name, @Param("password") String password);

    @Select("select user.* ,dept.name as deptName from user left join dept on user.deptId=dept.id where user.deptId=#{deptId}")
    List<Admin> findByDeptId(@Param("deptId")Integer deptId);
}
