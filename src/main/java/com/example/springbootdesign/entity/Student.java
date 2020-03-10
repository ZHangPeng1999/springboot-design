package com.example.springbootdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Entity
@NoArgsConstructor
@Data

public class Student {
    @Id
    private int id;
    private String name;
    private boolean selected;
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp"+" on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "student")
    private List<Elective> electives;
}
