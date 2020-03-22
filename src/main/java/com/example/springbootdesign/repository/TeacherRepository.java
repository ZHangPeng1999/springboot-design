package com.example.springbootdesign.repository;

import com.example.springbootdesign.entity.Teacher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher,Integer>{
    @Query("from Teacher t where t.teacherId=:teacherId")
    Teacher find(@Param("teacherId")Integer teacherId);

    @Modifying
    @Transactional
    @Query("update Teacher t set t.name=:name,t.password=:password where t.teacherId=:teacherId")
    Integer update(@Param("name")String name,@Param("password")String password,@Param("teacherId")Integer teacherId);


}
