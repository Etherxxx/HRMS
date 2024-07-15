package com.example.spring.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "sign")
public class Sign {
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

    @Column(name = "startTime")
    private String startTime;

    @Column(name = "endTime")
    private String endTime;

    @Column(name = "date")
    private String date;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Transient
    private String userName;

    @Transient
    private String deptName;

}
