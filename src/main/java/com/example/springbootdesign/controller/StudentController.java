package com.example.springbootdesign.controller;

import com.example.springbootdesign.component.RequestComponent;
import com.example.springbootdesign.entity.Course;
import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.service.CourseService;
import com.example.springbootdesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLOutput;
import java.util.*;
@Slf4j
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
    @GetMapping("directionsByTeacherId/{teacherId}")
    public Map getDirections(@PathVariable Integer teacherId){
        return Map.of("directions",courseService.listDirectionsByTeacherId(teacherId));
    }
    @GetMapping("directionsByStudentId/{StudentId}")
    public Map getDirectionsByStudentId(@PathVariable Integer StudentId){
        return Map.of("directions",courseService.listDirectionsByStudentId(StudentId));
    }
    @PatchMapping("teacher")
    public Map patchTeacher(@RequestBody Map<String, Object> teacher){
        Integer success = userService.updateStudent(requestComponent.getUid(), Integer.valueOf(teacher.get("teacherId").toString()));

        if(success==2){

            log.debug("{}",teacher.get("directions").toString());
            String directions = teacher.get("directions").toString();
            String substring = directions.substring(1, directions.length() - 1);
            log.debug(substring);
            String[] split = substring.split(", ");
            List<Integer> list=new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(Integer.valueOf(split[i]));
            }
            courseService.addDirectionElective(requestComponent.getUid(), list);
            return Map.of("message","成功");
        }else if(success==1)throw  new  ResponseStatusException(HttpStatus.UNAUTHORIZED,"您已选择导师，无法再次选择");
        else
        throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED,"此教师拒绝您的选择，请更换其他教师尝试");
    }

}
