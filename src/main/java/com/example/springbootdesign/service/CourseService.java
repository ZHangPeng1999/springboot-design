package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

//所有与课程/方向相关的服务都放在CourseService
@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseElectiveRepository courseElectiveRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private DirectionElectiveRepository directionElectiveRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    /**
     * 添加课程
     * @param teacherId
     * @param name
     * @param value
     * @param minn
     * @return
     */
    public Course addCourse(Integer teacherId, String name, Float value, Float minn){
        Teacher tea=teacherRepository.findById(teacherId).orElse(null);
        Course cou=new Course();
        cou.setName(name);
        cou.setValue(value);
        cou.setMinGrade(minn);
        cou.setTeacher(tea);
        courseRepository.save(cou);
        return cou;
    }

    /**
     * 更新课程信息
     * @param id
     * @param name
     * @param value
     * @param minn
     * @return
     */
    public Course updateCourse(Integer id,String name,Float value,Float minn){
        Course cou=courseRepository.findById(id).orElse(null);
        if(cou!=null) {
            cou.setName(name);
            cou.setValue(value);
            cou.setMinGrade(minn);
        }
        return cou;
    }

    /**
     * 添加方向
     * @param teacherId
     * @param name
     * @return
     */
    public Direction addDirection(Integer teacherId, String name){
        Direction dir=new Direction();
        Teacher tea=teacherRepository.findById(teacherId).orElse(null);
        dir.setName(name);
        dir.setTeacher(tea);
        directionRepository.save(dir);
        return dir;
    }
    public Direction addDirection(Direction direction){
        directionRepository.save(direction);
        return direction;
    }
    public Direction updateDirection(Integer id,String name){
        Direction dir=directionRepository.findById(id).orElse(null);
        if(dir!=null) {
            dir.setName(name);
        }
        return dir;
    }

    //   课程
    /**
     * 添加学生与课程关联
     * @param studentId
     * @param courseId
     * @return
     */
    public CourseElective addCourseElective(Integer studentId, Integer courseId){
        CourseElective courseElective =new CourseElective();
        Course course=courseRepository.findById(courseId).orElse(null);
        Student student=studentRepository.findById(studentId).orElse(null);
        if(student!=null&&course!=null){
            courseElective.setStudent(student);
            courseElective.setCourse(course);
            courseElectiveRepository.save(courseElective);
        }
        return courseElective;

    }
    /**
     * 某学生选择的所有课程
     * @param id
     * @return
     */
    public List<Course> listCourse(Integer id){
        return courseRepository.list(id);
    }

//  方向
    /**
     * 添加学生方向关联
     * @param studentId
     * @param directionId
     */
    public DirectionElective addDirectionElective(Integer studentId,Integer directionId){
        DirectionElective directionElective = new DirectionElective();
        Direction direction=directionRepository.findById(directionId).orElse(null);
        Student student= studentRepository.findById(studentId).orElse(null);
        if(student!=null&&direction!=null){
            directionElective.setDirection(direction);
            directionElective.setStudent(student);
            directionElectiveRepository.save(directionElective);
        }
        return directionElective;

    }
    /**
     * 返回所有方向
     * @return
     */
    public List<Direction> listDirections(){
        return directionRepository.list();
    }
    /**
     * 某学生选择的所有方向
     * @param id
     * @return
     */
    public List<Direction> listDirections(Integer id){
        return directionRepository.list(id);
    }

    public Course addCourse(Course course) {
        courseRepository.save(course);
        courseRepository.refresh(course);
        return  course;
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }
}
