package com.example.springbootdesign.controller;

import com.example.springbootdesign.component.MyToken;
import com.example.springbootdesign.component.RequestComponent;
import com.example.springbootdesign.entity.Course;
import com.example.springbootdesign.entity.Direction;
import com.example.springbootdesign.entity.Student;
import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.service.CourseService;
import com.example.springbootdesign.service.UserService;
import com.sun.xml.txw2.output.DumbEscapeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/teacher/")
public class TeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private CourseService courseService;

    @PostMapping("course")
    public Map postCourse(@RequestBody Course course){
        course.setTeacher(new Teacher(requestComponent.getUid()));
        courseService.addCourse(course);
        return Map.of("course",course);
    }
    @PostMapping("direction")
    public Map postDirection(@RequestBody Direction direction){
        direction.setTeacher(new Teacher(requestComponent.getUid()));
        courseService.addDirection(direction);
        return  Map.of("direction", direction);
    }
    @PostMapping("student")
    public Map postStudent(@RequestBody Student student){
        userService.addStudent(student);
        return Map.of("student",student);
    }
    @PostMapping("preStudent")
    public Map postPreStudent(@RequestBody Student student){
        userService.addPreStudent(student.getUser().getNumber(),requestComponent.getUid(),student.getUser().getName());
        return Map.of("student",student);
    }
    @GetMapping("students")
    public Map getStudents(){
        List<Student> students = userService.listStudents(requestComponent.getUid());
        return Map.of("students",students);
    }
    @GetMapping("courses")
    public Map getCourses(){
        return Map.of("courses",courseService.listCourse(requestComponent.getUid()));
    }
    @GetMapping("directions")
    public Map getDirections(){
        return Map.of("directions",courseService.listDirections(requestComponent.getUid()));
    }

    @GetMapping("index")
    public Map  getTeacher(){
        Teacher t=userService.getTeacherById(requestComponent.getUid());
        log.debug(String.valueOf(t.getId()));
//        List<Course> courses=courseService.listCourses(requestComponent.getUid());
        //spring.jpa.open-in-view=true 默认可以在非事务对象中获取延迟加载数据 但是不可以修改
        return Map.of("teacher",t,//只是在序列化t这个对象的时候不会序列化course
                "courses",t.getCourses(),//所以仍然有coursees这个属性
                "students",t.getStudents(),
                "directions",t.getDirections());
    }
}
