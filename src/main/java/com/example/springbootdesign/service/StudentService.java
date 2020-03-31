package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {
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
//  学生
    /**
     * 添加学生
     * @param studentId
     * @param name
     */
    private void addStudent(Integer studentId,String name){
        Student stu=new Student();
        stu.setName(name);
        stu.setStudentId(studentId);
        studentRepository.save(stu);
    }

    /**
     * 学生选择导师
     * @param studentId
     * @param teacherId
     * @return 选择是否成功
     */
    private Boolean updateStudent(Integer studentId,Integer teacherId){
        Student stu=studentRepository.find(studentId);
        if(stu.getIsSelectRoot().equals(true)){
            Teacher tea=teacherRepository.findById(teacherId).orElse(null);
            if(tea.getStudents().size()<tea.getSelectStudentNum()) {
                stu.setTeacher(tea);
                return true;
            }
        }
        return false;
    }

    /**
     * 修改学生学号姓名
     * @param id
     * @param studentId
     * @param name
     */
    private void updateStudent(Integer id,Integer studentId,String name){
        Student stu=studentRepository.findById(id).orElse(null);
        stu.setName(name);
        stu.setStudentId(studentId);
    }

//   课程
    /**
     * 添加学生与课程关联
     * @param studentId
     * @param courseId
     * @return
     */
    private CourseElective addCourseElective(Integer studentId, Integer courseId){
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
    private List<Course> listCourse(Integer id){
        return courseRepository.list(id);
    }

//  方向
    /**
     * 添加学生方向关联
     * @param studentId
     * @param directionId
     */
    private DirectionElective addDirectionElective(Integer studentId,Integer directionId){
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
    private List<Direction> listDirections(){
        return directionRepository.list();
    }
    /**
     * 某学生选择的所有方向
     * @param id
     * @return
     */
    private List<Direction> listDirections(Integer id){
        return directionRepository.list(id);
    }

}
