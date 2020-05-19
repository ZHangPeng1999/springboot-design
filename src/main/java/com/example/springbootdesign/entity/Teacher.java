package com.example.springbootdesign.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"students","courses","directions"})
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Integer SelectStudentNum;//能选择的学生上限
    private Integer WantStudentNum;//希望选择的学生数

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @MapsId
    private User user;
    @OneToMany(mappedBy = "teacher")
    private List<Student> students;//已选择学生 已选择学生数目直接统计list总数即可
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
    @OneToMany(mappedBy = "teacher")
    private List<Direction>  directions;


    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp"+" on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

    public Teacher(int uid) {
        this.id=uid;
    }
}
