package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private DirectionElectiveRepository directionElectiveRepository;

    /**
     * 添加教师
     * @param teacherId
     * @param name
     * @param password
     * @param SelectStudentNum
     * @param WantStudentNum
     */
    private Teacher add(Integer teacherId,String name,String password,Integer SelectStudentNum,Integer WantStudentNum){
        Teacher tea=new Teacher();
        tea.setName(name);
        tea.setTeacherId(teacherId);
        tea.setPassword(password);
        tea.setSelectStudentNum(SelectStudentNum);
        tea.setWantStudentNum(WantStudentNum);
        teacherRepository.save(tea);
        return tea;
    }

    /**
     * 添加教师
     * @param teacherId
     * @param name
     * @param password
     */
    private Teacher add(Integer teacherId,String name,String password) {
        Teacher tea = new Teacher();
        tea.setName(name);
        tea.setTeacherId(teacherId);
        tea.setPassword(password);
        teacherRepository.save(tea);
        return tea;
    }

    /**
     * 修改教师密码 姓名
     */
    private Teacher update(Integer id,String name,String password){
        Teacher tea=teacherRepository.findById(id).orElse(null);
        if (tea!=null){
            tea.setName(name);
            tea.setPassword(password);
        }
        return tea;
    }
    /**
     * 修改密码
     */
    private Teacher  update(Integer teacherId,String password){
        Teacher tea = teacherRepository.findById(teacherId).orElse(null);
        if (tea!=null) {
            tea.setPassword(password);
            teacherRepository.save(tea);
        }
        return tea;
    }

    /**
     * 修改学生数 范围数
     */
    private Teacher update(Integer id,Integer selectNum,Integer wantNum){
        Teacher tea = teacherRepository.findById(id).orElse(null);
        if (tea!=null){
            tea.setSelectStudentNum(selectNum);
            tea.setWantStudentNum(wantNum);
        }
        teacherRepository.save(tea);
        return  tea;
    }

    /**
     * 查找选择老师的学生列表
     * @param id
     * @return
     */
    private List<Student> list(Integer id){
        Teacher tea=teacherRepository.findById(id).orElse(null);
        List<Student> students=new ArrayList<>();
        if(tea!=null){
            students=tea.getStudents();
        }
        return students;
    }


    /**
     * 添加课程
     * @param teacherId
     * @param name
     * @param value
     * @param minn
     * @return
     */
    private Course add(Integer teacherId,String name,Float value,Float minn){
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
    private Course update(Integer id,String name,Float value,Float minn){
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
    private Direction add(Integer teacherId,String name){
        Direction dir=new Direction();
        Teacher tea=teacherRepository.findById(teacherId).orElse(null);
        dir.setName(name);
        dir.setTeacher(tea);
        directionRepository.save(dir);
        return dir;
    }
    private Direction updateDirection(Integer id,String name){
        Direction dir=directionRepository.findById(id).orElse(null);
        if(dir!=null) {
            dir.setName(name);
        }
        return dir;
    }
}
