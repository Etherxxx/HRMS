package com.example.spring.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "audit")
public class Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private String time;

    @Column(name = "day")
    private String day;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;

    @Transient
    private String userName;

}
