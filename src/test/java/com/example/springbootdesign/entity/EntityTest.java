package com.example.springbootdesign.entity;

import com.example.springbootdesign.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
public class EntityTest {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseElectiveRepository courseElectiveRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private DirectionElectiveRepository directionElectiveRepository;
    @Test
    public void init(){


    }
    @Test
    public void test_teacherToStudent(){
        Teacher tea = teacherRepository.findById(1).orElse(null);
        Student stu = studentRepository.findById(2017214215).orElse(null);
        stu.setTeacher(tea);
        studentRepository.save(stu);

    }
    @Test
    public void test_CourseToStu(){
        Course cou = courseRepository.findById(1).orElse(null);
        Student stu = studentRepository.findById(2017214215).orElse(null);
        CourseElective courseElective = new CourseElective();
        courseElective.setCourse(cou);
        courseElective.setStudent(stu);
        courseElective.setGrade((float) 92.0);
        courseElectiveRepository.save(courseElective);

    }
    @Test
    public void test_DirectionToStudent(){
        Direction direction = directionRepository.findById(1).orElse(null);
        Student stu = studentRepository.findById(2017214215).orElse(null);
        DirectionElective elective = new DirectionElective();
        elective.setDirection(direction);
        elective.setStudent(stu);
        directionElectiveRepository.save(elective);

    }
}
