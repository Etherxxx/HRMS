package com.example.spring.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "deptId")
    private Integer deptId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "basic")
    private Float basic;

    @Column(name = "bonus")
    private Float bonus;

    @Column(name = "allowance")
    private Float allowance;

    @Column(name = "insurence")
    private Float insurence;

    @Column(name = "fine")
    private Float fine;

    @Column(name = "total")
    private Float total;

    @Column(name = "date")
    private String date;

    @Transient
    private String userName;

    @Transient
    private String deptName;

}
