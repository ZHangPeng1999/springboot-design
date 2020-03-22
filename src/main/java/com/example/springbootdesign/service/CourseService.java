package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.Course;
import com.example.springbootdesign.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    /**
     * 添加课程
     * @param name
     * @param value
     * @param minn
     */
    private void add(String name,Float value,Float minn){
        Course cou=new Course();
        cou.setName(name);
        cou.setValue(value);
        cou.setMinGrade(minn);
        courseRepository.save(cou);
    }
    private void update(Integer id,String name,Float value,Float minn){
        Course cou=courseRepository.findById(id).orElse(null);
        cou.setName(name);
        cou.setValue(value);
        cou.setMinGrade(minn);
    }
}
