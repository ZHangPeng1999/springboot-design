package com.example.springbootdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private int grade;//课程成绩
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;



}
