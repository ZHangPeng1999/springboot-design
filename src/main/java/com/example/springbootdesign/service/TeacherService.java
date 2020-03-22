package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.TabExpander;
import java.util.List;

@Service
@Transactional
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    /**
     * 添加教师
     * @param teacherId
     * @param name
     * @param password
     * @param SelectStudentNum
     * @param WantStudentNum
     */
    private void add(Integer teacherId,String name,String password,Integer SelectStudentNum,Integer WantStudentNum){
        Teacher tea=new Teacher();
        tea.setName(name);
        tea.setTeacherId(teacherId);
        tea.setPassword(password);
        tea.setSelectStudentNum(SelectStudentNum);
        tea.setWantStudentNum(WantStudentNum);
        teacherRepository.save(tea);
    }

    /**
     * 添加教师
     * @param teacherId
     * @param name
     * @param password
     */
    private void add(Integer teacherId,String name,String password) {
        Teacher tea = new Teacher();
        tea.setName(name);
        tea.setTeacherId(teacherId);
        tea.setPassword(password);
        teacherRepository.save(tea);
    }

    /**
     * 修改教师密码 姓名
     * @param teacherId
     * @param name
     * @param password
     */
    private void update(Integer teacherId,String name,String password){
        teacherRepository.update(name, password, teacherId);
    }
    /**
     * 修改密码
     * @param teacherId
     * @param newPassword
     */
    private void  update(Integer teacherId,String newPassword){
        Teacher tea = teacherRepository.find(teacherId);
        tea.setPassword(newPassword);
        teacherRepository.save(tea);
    }

    /**
     * 修改学生数 范围数
     * @param teacherId
     * @param selectNum
     * @param wantNum
     */
    private void update(Integer teacherId,Integer selectNum,Integer wantNum){
        Teacher tea = teacherRepository.find(teacherId);
        tea.setSelectStudentNum(selectNum);
        tea.setWantStudentNum(wantNum);
        teacherRepository.save(tea);
    }

    /**
     * 查找选择老师的学生列表
     * @param id
     * @return
     */
    private List<Student> list(Integer id){
        Teacher tea=teacherRepository.findById(id).orElse(null);
        List<Student> students=tea.getStudents();
        return students;
    }


}
