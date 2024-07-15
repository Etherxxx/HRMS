package com.example.spring.dao;

import com.example.spring.entity.Notice;
import com.example.spring.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NoticeDao extends Mapper<Notice> {
    List<Notice> findBySearch(@Param("params") Params params);

    @Select("select * from notice order by time desc limit 3")
    List<Notice> findNew3();
}
