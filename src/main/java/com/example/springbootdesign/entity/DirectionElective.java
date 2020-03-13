package com.example.springbootdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 方向选择表
 */
@Entity
@Data
@NoArgsConstructor
public class DirectionElective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String detail;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Direction direction;
}
