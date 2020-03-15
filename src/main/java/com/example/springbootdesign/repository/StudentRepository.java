package com.example.springbootdesign.repository;

import com.example.springbootdesign.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<Student,Integer>{
}
