package com.example.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AttendanceStatus {
    private Integer userId;

    private String date;

    private String status;

    private String phone;

    private String userName;

    private String deptName;

}
