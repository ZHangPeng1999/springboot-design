package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.Student;
import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.repository.DirectionElectiveRepository;
import com.example.springbootdesign.repository.StudentRepository;
import com.example.springbootdesign.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;

@Service
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private DirectionElectiveRepository directionElectiveRepository;
    private void add(Integer studentId,String name){
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
    private Boolean update(Integer studentId,Integer teacherId){
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
    private void update(Integer id,Integer studentId,String name){
        Student stu=studentRepository.findById(id).orElse(null);
        stu.setName(name);
        stu.setStudentId(studentId);
    }

}
