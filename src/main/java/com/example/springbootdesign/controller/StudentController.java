package com.example.springbootdesign.controller;

import com.example.springbootdesign.component.RequestComponent;
import com.example.springbootdesign.entity.Course;
import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.service.CourseService;
import com.example.springbootdesign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Map;

@RestController
@RequestMapping("/api/student/")
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RequestComponent requestComponent;
    @GetMapping("teachers")
    public Map getTeachers(){
        return Map.of("teachers",userService.listTeachers());
    }
    @GetMapping("directions/{teacherNum}")
    public Map getDirections(@PathVariable Integer teacherNum){
        return Map.of("directions",courseService.listDirections(teacherNum));
    }
    @PostMapping("teacher")
    public Map postTeacher(@RequestBody Teacher teacher){
        return Map.of("teacher",userService.updateStudent(requestComponent.getUid(), teacher.getId()));
    }
}
