package com.example.spring.dao;


import com.example.spring.entity.Audit;
import com.example.spring.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AuditDao extends Mapper<Audit> {
    List<Audit> findBySearch(@Param("params") Params params);
}
