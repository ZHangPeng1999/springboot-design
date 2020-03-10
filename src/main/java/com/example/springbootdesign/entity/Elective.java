package com.example.springbootdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 选课表
 */
@Entity
@NoArgsConstructor
@Data
public class Elective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String detail;
    private int grade;
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)

    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp"+" on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;

}
