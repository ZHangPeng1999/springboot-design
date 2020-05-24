package com.example.springbootdesign.controller;

import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.repository.CourseRepository;
import com.example.springbootdesign.service.CourseService;
import com.example.springbootdesign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.CheckedOutputStream;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @GetMapping("teachers")
    public Map getTeachers(){
        return Map.of("teachers", userService.listTeachers());
    }
    @GetMapping("index")
    public Map getAdmin(){
        return Map.of("teachers",userService.listTeachers(),
                "students",userService.listStudents(),
                "courses", courseService.listCourses(),
                "directions",courseService.listDirections());
    }
    @PostMapping("teacher")
    public Map postTeacher(@RequestBody Teacher teacher){
        userService.addTeacher(teacher);
        return Map.of("teacher",teacher);
    }

}
