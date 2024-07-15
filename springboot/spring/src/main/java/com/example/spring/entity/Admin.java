package com.example.spring.entity;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Component
@Table(name = "user")
public class Admin {
    //代表是主键且自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private Integer age;
    @Column(name = "sex")
    private String sex;
    @Column(name = "phone")
    private String phone;
    @Column(name = "role")
    private String role;
    @Column(name="deptId")
    private Integer deptId;
    @Transient
    private String token;
    @Transient
    private String deptName;
    @Transient
    private String roleRemark;

}
