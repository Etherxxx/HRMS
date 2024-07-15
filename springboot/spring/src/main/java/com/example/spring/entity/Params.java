package com.example.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Params {
    private String name;
    private String phone;
    private Integer deptId;
    private String managerName;
    private String username;
    private String portray;
    private String month;
    private Integer userId;
    private Integer pageNum;
    private Integer pageSize;
}
