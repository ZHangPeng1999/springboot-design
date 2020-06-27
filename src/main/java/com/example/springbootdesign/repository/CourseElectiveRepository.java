package com.example.springbootdesign.repository;

import com.example.springbootdesign.entity.CourseElective;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CourseElectiveRepository extends BaseRepository<CourseElective,Integer>{
    @Query("select c from CourseElective c where c.course.id=:cid")
    public List<CourseElective> listByCid(@Param("cid")Integer cid);
    @Query("select c from CourseElective c where c.student.user.id=:sid")
    public List<CourseElective> listBySid(@Param("sid")Integer sid);
    @Modifying
    @Query("delete from CourseElective c where c.student.id=:sid and c.course.id=:cid")
    public void delete(@Param("sid")Integer sid,@Param("cid")Integer cid);

}
