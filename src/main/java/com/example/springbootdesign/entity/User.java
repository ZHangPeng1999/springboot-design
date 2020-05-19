package com.example.springbootdesign.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class User {
    public enum Role{
        ADMIN,STUDENT,TEACHER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//拒绝序列化
    private Role role;
    //学号/工号
    @Column(unique = true)
    private Integer number;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp"+" on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
}
