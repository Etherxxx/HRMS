package com.example.spring.dao;


import com.example.spring.entity.Params;
import com.example.spring.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoleDao extends Mapper<Role> {
    List<Role> findBySearch(@Param("params") Params params);
}
