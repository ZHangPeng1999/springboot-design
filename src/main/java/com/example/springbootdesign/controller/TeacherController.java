package com.example.springbootdesign.controller;

import com.example.springbootdesign.component.MyToken;
import com.example.springbootdesign.component.RequestComponent;
import com.example.springbootdesign.entity.Course;
import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.service.CourseService;
import com.example.springbootdesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping("welcome")
    public void  getIndex(HttpServletRequest request){
        log.debug("{}",requestComponent.getUid());//通过组件
    }
    @PostMapping("course")
    public Map postCourse(@RequestBody Course course){
        course.setTeacher(new Teacher(requestComponent.getUid()));
        courseService.addCourse(course);
        return Map.of("course",course);
    }

    @GetMapping("index")
    public Map  getTeacher(){
        Teacher t=userService.getTeacherById(requestComponent.getUid());
        log.debug(String.valueOf(t.getId()));
//        List<Course> courses=courseService.listCourses(requestComponent.getUid());
        //spring.jpa.open-in-view=true 默认可以在非事务对象中获取延迟加载数据 但是不可以修改
        return Map.of("teacher",t,//只是在序列化t这个对象的时候不会序列化course
                "courses",t.getCourses(),//所以仍然有coursees这个属性
                "students",t.getStudents());
    }
}
