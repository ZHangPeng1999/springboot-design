package com.example.springbootdesign.controller;

import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.entity.User;
import com.example.springbootdesign.repository.CourseRepository;
import com.example.springbootdesign.service.CourseService;
import com.example.springbootdesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.CheckedOutputStream;
@Slf4j
@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    private PasswordEncoder encoder;
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
    public Map postTeacher(@RequestBody Map<String,String> tea){
        log.debug("{}",tea);
        Teacher teacher = new Teacher();
        teacher.setPassword(encoder.encode(tea.get("password")));
        User user = new User();
        teacher.setUser(user);
        teacher.getUser().setNumber(Integer.valueOf(tea.get("number")));
        teacher.getUser().setName(tea.get("name"));
        teacher.setSelectStudentNum(Integer.valueOf(tea.get("selectStudentNum")));
        teacher.setWantStudentNum(Integer.valueOf(tea.get("wantStudentNum")));
        userService.addTeacher(teacher);
        return Map.of("teacher",teacher);
    }
    @PatchMapping("teacher")
    public Map patchTeacher(@RequestBody Map<String,String> teacher){
        userService.updateTeacher(Integer.valueOf(teacher.get("id")), teacher.get("name"),Integer.valueOf(teacher.get("number")),encoder.encode(teacher.get("password")));
        Teacher tea=userService.updateTeacher(Integer.valueOf(teacher.get("id")),Integer.valueOf(teacher.get("selectNum")),Integer.valueOf(teacher.get("wantNum")));
        return Map.of("teacher",tea);
    }
    @DeleteMapping("teacher/{Tid}")
    public Boolean deleteTeacher(@PathVariable Integer Tid){
        return  userService.deleteTeacher(Tid);
    }
}
