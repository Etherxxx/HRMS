package com.example.spring.dao;


import com.example.spring.entity.Params;
import com.example.spring.entity.Sign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SignDao extends Mapper<Sign> {
    List<Sign> findBySearch(@Param("params") Params params);

    @Select("select * from sign where userId=#{userId} and data_format(date,'%Y%m%d')=#{date}")
    Sign findByUserIdAndDate(@Param("userId") Integer userId, @Param("date") String date);
}
