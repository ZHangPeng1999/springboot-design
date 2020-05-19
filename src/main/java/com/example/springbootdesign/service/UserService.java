package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//所有与用户相关的服务 都放在UserService 包括 学生/老师等
@Service
@Transactional
public class UserService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
//    用户
    public User getUser(Integer num){
        return   userRepository.find(num);
    }
//  老师
    public Teacher getTeacherById(Integer id){
        return teacherRepository.findById(id).orElse(null);
    }
    public Teacher getTeacherByNum(Integer number){
        return teacherRepository.find(number);
    }
    /**
     * 添加教师
     * @param teacherId
     * @param name
     * @param password
     * @param SelectStudentNum
     * @param WantStudentNum
     */
    public Teacher addTeacher(Integer teacherId, String name, String password, User.Role role, Integer SelectStudentNum, Integer WantStudentNum){
        Teacher tea=new Teacher();
        User user=new User();
        user.setName(name);
        user.setNumber(teacherId);
        user.setRole(role);
        tea.setUser(user);
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
    public Teacher addTeacher(Integer teacherId,String name,String password) {
        Teacher tea = new Teacher();
        tea.getUser().setName(name);
        tea.getUser().setNumber(teacherId);
        tea.setPassword(password);
        teacherRepository.save(tea);
        return tea;
    }
    public Teacher addTeacher(Teacher teacher,User user){
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }

    /**
     * 修改教师密码 姓名
     */
    public Teacher updateTeacher(Integer id,String name,String password){
        Teacher tea=teacherRepository.findById(id).orElse(null);
        if (tea!=null){
            tea.getUser().setName(name);
            tea.setPassword(password);
        }
        return tea;
    }
    /**
     * 修改密码
     */
    public Teacher  updateTeacher(Integer teacherId,String password){
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
    public Teacher updateTeacher(Integer id,Integer selectNum,Integer wantNum){
        Teacher tea = teacherRepository.findById(id).orElse(null);
        if (tea!=null){
            tea.setSelectStudentNum(selectNum);
            tea.setWantStudentNum(wantNum);
        }
        teacherRepository.save(tea);
        return  tea;
    }

    /**
     * 添加内定学生
     * @param studentId
     * @param TeacherId
     * @param StudentName
     * @return
     */
    public Student preAddStudent(Integer studentId, Integer TeacherId, String StudentName){
        Student stu=studentRepository.find(studentId);
        Teacher tea=teacherRepository.findById(TeacherId).orElse(null);
        if(stu==null){
            stu=new Student();
            User user=new User();
            user.setName(StudentName);
            user.setNumber(studentId);
            stu.setUser(user);
            stu.setTeacher(tea);
            studentRepository.save(stu);
        } else{
            stu.setTeacher(tea);
        }
        return stu;
    }


    /**
     * 查找选择老师的学生列表
     * @param id
     * @return
     */
    public List<Student> listStudents(Integer id){
        Teacher tea=teacherRepository.findById(id).orElse(null);
        if(tea!=null){
            List<Student> students=tea.getStudents();
            return students;
        }
        return List.of();
    }



    /**
     * 获取所有学生列表 并根据课程加权分排序 删除某课程分数小于要求最低分的学生
     * @return
     */
    public List<Student> sortStudent(){
        List<Student> students=new ArrayList<>();
        return students;
    }
    //  学生
    /**
     * 添加学生
     * @param studentId
     * @param name
     */
    public void addStudent(Integer studentId,String name){
        Student stu=new Student();
        User user=new User();
        user.setName(name);
        user.setRole(User.Role.STUDENT);
        user.setNumber(studentId);
        stu.setUser(user);
        studentRepository.save(stu);
    }

    /**
     * 学生选择导师
     * @param studentId
     * @param teacherId
     * @return 选择是否成功
     */
    public Boolean updateStudent(Integer studentId,Integer teacherId){
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
    public void updateStudent(Integer id,Integer studentId,String name){
        Student stu=studentRepository.findById(id).orElse(null);
        stu.getUser().setName(name);
        stu.getUser().setNumber(studentId);
    }


}
