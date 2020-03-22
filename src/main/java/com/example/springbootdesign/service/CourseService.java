package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.CourseRepository;
import com.example.springbootdesign.repository.ElectiveRepository;
import com.example.springbootdesign.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ElectiveRepository electiveRepository;
    @Autowired
    private StudentRepository studentRepository;
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
    private void update(Integer studentId,Integer courseId){
        Elective elective=new Elective();
        Course course=courseRepository.findById(courseId).orElse(null);
        Student student=studentRepository.findById(studentId).orElse(null);
        elective.setStudent(student);
        elective.setCourse(course);
        electiveRepository.save(elective);
    }
}
