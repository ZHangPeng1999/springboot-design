package com.example.springbootdesign.repository;

import com.example.springbootdesign.entity.Course;
import com.example.springbootdesign.entity.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends BaseRepository<Course,Integer> {
    @Query("select c from Course c")
    List<Course> list();
    @Query("select c.course from CourseElective c where c.student.id=:id")
    List<Course> list(@Param("id")Integer id);
    @Query("select c from Course c where c.teacher.user.id=:id")
    List<Course> listByTid(@Param("id")Integer id);
}
