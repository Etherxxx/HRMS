package com.example.spring.entity;

import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private String time;

    @Column(name = "username")
    private String username;

}
