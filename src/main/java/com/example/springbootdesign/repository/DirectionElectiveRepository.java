package com.example.springbootdesign.repository;

import com.example.springbootdesign.entity.CourseElective;
import com.example.springbootdesign.entity.DirectionElective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DirectionElectiveRepository extends BaseRepository<DirectionElective,Integer> {
    @Query("select c from DirectionElective c where c.direction.id=:cid")
    public List<DirectionElective> listByDid(@Param("cid")Integer cid);
    @Query("select c from DirectionElective c where c.student.id=:sid")
    public List<DirectionElective> listBySid(@Param("sid")Integer sid);
}
