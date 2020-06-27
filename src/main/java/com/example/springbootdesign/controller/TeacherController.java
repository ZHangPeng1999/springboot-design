package com.example.springbootdesign.controller;

import com.example.springbootdesign.component.RequestComponent;
import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.DirectionElectiveRepository;
import com.example.springbootdesign.service.CourseService;
import com.example.springbootdesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
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
    @Autowired
    private PasswordEncoder encoder;
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
    public Map postStudent(@RequestBody Map<String,String> student){
        userService.addStudent(Integer.valueOf(student.get("number")),student.get("name"));
        return Map.of("student",student);
    }
    @PostMapping("preStudent")
    public Map postPreStudent(@RequestBody Map<String,String> student){
        userService.addPreStudent(Integer.valueOf(student.get("number")),requestComponent.getUid(),student.get("name"));
        return Map.of("student",student);
    }
    @GetMapping("selectStudents")
    public Map getSelectStudents(){
        List<Student> students = userService.listStudents(requestComponent.getUid());
        return Map.of("students",students);
    }
    @GetMapping("students")
    public Map getStudents(){
        List<Student> students = userService.listStudents();
        return  Map.of("students",students);
    }
    @GetMapping("courses")
    public Map getCourses(){
        return Map.of("courses",courseService.listCourseByTeacherId(requestComponent.getUid()));
    }
    @GetMapping("directions")
    public Map getDirections(){
        return Map.of("directions",courseService.listDirectionsByTeacherId(requestComponent.getUid()));
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
    @PatchMapping("teacher")
    public  Map patchTeacher(@RequestBody Map<String,String> teacher){
        userService.updateTeacher(requestComponent.getUid(), teacher.get("name"), Integer.valueOf(teacher.get("number")),encoder.encode(teacher.get("password")));
        userService.updateTeacher(requestComponent.getUid(), Integer.valueOf(teacher.get("selectStudentNum")), Integer.valueOf(teacher.get("wantStudentNum")));
        Teacher t=userService.getTeacherById(requestComponent.getUid());

        return Map.of("teacher",t);
    }
    @PatchMapping("student")
    public Map patchStudent(@RequestBody Map<String,String> student){
        Student stu=null;
        stu=userService.updateStudent(Integer.valueOf(student.get("id")), Integer.valueOf(student.get("studentId")), student.get("name"));
        return Map.of("student",stu);
    }
    @DeleteMapping("course/{Cid}")
    public Boolean deleteCourse(@PathVariable Integer Cid){
        return  courseService.deleteCourse(Cid);
    }
    @DeleteMapping("direction/{Did}")
    public Boolean deleteDirection(@PathVariable Integer Did){
        return  courseService.deleteDirection(Did);
    }
    @PatchMapping("course")
    public Course patchCourse(@RequestBody Map<String,String> course){
        return courseService.updateCourse(Integer.valueOf(course.get("id")), course.get("name"), Float.valueOf(course.get("value")), Float.valueOf(course.get("minGrade")));
    }
    @PatchMapping("direction")
    public Direction patchDirection(@RequestBody Map<String,String> direction){
        return courseService.updateDirection(Integer.valueOf(direction.get("id")), direction.get("name"));
    }
    @PostMapping("courseElective")
    public CourseElective postCourseElective(@RequestBody Map<String,String> courseElective){
        log.debug("{}",courseElective);
        if(courseElective.containsKey("detail")==false) courseElective.put("deatail", "");
        return courseService.addCourseElective(Integer.valueOf(courseElective.get("number")), Integer.valueOf(courseElective.get("cId")),Float.valueOf(courseElective.get("grade")),courseElective.get("detail"));
    }
    @GetMapping("courseElectiveByStudentId/{sid}")
    public List<CourseElective> getCourseElectiveByStudentId(@PathVariable Integer sid){
        return courseService.listCourseElectiveBySid(sid);
    }
    @GetMapping("courseElectiveByCourseId/{cid}")
    public List<CourseElective> getCourseElectiveByCourseId(@PathVariable Integer cid){
        return courseService.listCourseElectiveByCid(cid);
    }
    @GetMapping("directionElectiveByStudentId/{sid}")
    public List<DirectionElective> getDirectionByStudentId(@PathVariable Integer sid){
        return courseService.listDirectionElectiveBySid(sid);
    }
    @GetMapping("directionElectiveByDirectionId/{did}")
    public List<DirectionElective> getCourseElectiveByDirectionId(@PathVariable Integer did){
        return courseService.listDirectionElectiveByDid(did);
    }
    @DeleteMapping("courseElective/{cid}")
    public Boolean deleteCourseElectiveByStudentId(@PathVariable Integer cid){
        courseService.deleteCourseElective(cid);
        return  true;
    }
    @PatchMapping("courseElective")
    public Map patchCourseElectiveByStudentId(@RequestBody Map<String,String> courseElective){
        return Map.of("courseElective", courseService.updateCourseElective(Integer.valueOf(courseElective.get("id")),Float.valueOf(courseElective.get("grade")),courseElective.get("detail") ));
    }
    @PostMapping("courseElectiveByXlsx")
    public Boolean postCourseElectiveByXlsx(@RequestBody Map<String,List<Map<String,String>>> map){
        map.get("students").forEach(student->{
            courseService.addCourseElective(Integer.valueOf(student.get("number")),student.get("name"),Integer.valueOf(student.get("cid")),Float.valueOf(student.get("grade")));
        });
        return true;
    }

}
