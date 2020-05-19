package com.example.springbootdesign.repository;

import com.example.springbootdesign.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends BaseRepository<User,Integer> {
    @Query("from User u where u.number=:num")
    User find(@Param("num")Integer num);
}
