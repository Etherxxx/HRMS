package com.example.spring.dao;


import com.example.spring.entity.Log;
import com.example.spring.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface LogDao extends Mapper<Log> {
    List<Log> findBySearch(@Param("params") Params params);
}
