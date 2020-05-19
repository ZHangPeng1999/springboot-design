package com.example.springbootdesign.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"courseElectives","directionElectives"})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isSelectRoot;//是否在老师选择范围内



    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @MapsId
    private User user;
    @ManyToOne
    private Teacher teacher;//如果这个外键不为空则为被选中
    @OneToMany(mappedBy = "student")
    private List<CourseElective> courseElectives;
    @OneToMany(mappedBy = "student")
    private List<DirectionElective> directionElectives;



    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp"+" on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;


}
