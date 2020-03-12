package com.example.springbootdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int value;//课程权值
    private int minGrade;//课程最低分

    @OneToMany(mappedBy = "course")
    private List<Elective> electives;
}
