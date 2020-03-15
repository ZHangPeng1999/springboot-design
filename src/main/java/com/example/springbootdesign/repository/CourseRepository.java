package com.example.springbootdesign.repository;

import com.example.springbootdesign.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<Course,Integer> {
}
