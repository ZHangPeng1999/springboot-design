package com.example.springbootdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@NoArgsConstructor
@Data

public class Student {
    @Id
    private int id;
    private String name;
    private boolean isSelectRoot;//是否能被选择
    @ManyToOne
    private Teacher teacher;//如果这个外键不为空则为被选中
    @OneToMany(mappedBy = "student")
    private List<Elective> electives;
    @OneToMany(mappedBy = "student")
    private List<DirectionElective> directionElectives;


}
