package com.example.springbootdesign.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"directionElectives"})
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "direction")
    private List<DirectionElective> directionElectives;
    @ManyToOne
    private Teacher teacher;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp"+" on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
}
