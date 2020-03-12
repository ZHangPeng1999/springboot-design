package com.example.springbootdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private int SelectStudentNum;//能选择的学生上限
    private int WantStudentNum;//希望选择的学生数
    @OneToMany(mappedBy = "teacher")
    private List<Student> students;//已选择学生 已选择学生数目直接统计list总数即可

}
