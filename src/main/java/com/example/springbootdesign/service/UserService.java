package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.*;
import com.example.springbootdesign.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

//所有与用户相关的服务 都放在UserService 包括 学生/老师等
@Service
@Transactional
@Slf4j
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
        tea.getUser().setRole(User.Role.TEACHER);
        tea.setPassword(password);
        teacherRepository.save(tea);
        return tea;
    }
    public Teacher addTeacher(Teacher teacher,User user){
        if(user.getRole()!= User.Role.ADMIN){
            user.setRole(User.Role.TEACHER);
        }
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }
    public Teacher addTeacher(Teacher teacher){
        teacher.getUser().setRole(User.Role.TEACHER);
        return teacherRepository.save(teacher);
    }
    /**
     * 修改教师密码 姓名
     */
    public Teacher updateTeacher(Integer id,String name,Integer number,String password){
        Teacher tea=teacherRepository.findById(id).orElse(null);
        if (tea!=null){
            tea.getUser().setNumber(number);
            tea.getUser().setName(name);
            tea.setPassword(password);
        }
        return tea;
    }
    public Boolean deleteTeacher(Integer Tid){
        teacherRepository.deleteById(Tid);
        return  true;
    }
    /**
     * 修改密码
     */
    public Teacher  updateTeacher(Integer teacherId, String password){
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
    public Student addPreStudent(Integer studentId, Integer TeacherId, String StudentName){
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
    public List<Teacher> listTeachers(){
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers;
    }



    //  学生
    /**
     * 添加学生
     * @param studentId
     * @param name
     */
    public Student addStudent(Integer studentId,String name){
        Student stu=new Student();
        User user=new User();
        user.setName(name);
        user.setRole(User.Role.STUDENT);
        user.setNumber(studentId);
        stu.setUser(user);
        studentRepository.saveAndFlush(stu);
        studentRepository.refresh(stu);
        return stu;
    }
    public void addStudent(Student student){
        student.getUser().setRole(User.Role.STUDENT);
         studentRepository.save(student);
    }

    /**
     * 学生选择导师
     * @param studentId
     * @param teacherId
     * @return 选择是否成功
     */
    public Integer updateStudent(Integer studentId,Integer teacherId){
        Lock lock=new ReentrantLock();
        lock.lock();
        try {
        List<Student> students = studentRepository.findAll();
        students = students.stream()
                .filter(x -> {
                    int f = 1;
                    for (int i = 0; i < x.getCourseElectives().size(); i++) {
                        Float minGrade = x.getCourseElectives().get(i).getCourse().getMinGrade();
                        if (x.getCourseElectives().get(i).getCourse().getTeacher().getId()==teacherId&&x.getCourseElectives().get(i).getGrade() < minGrade) {
                            f = 0;
                            break;
                        }
                    }
                    if(x.getTeacher()!=null)f=0;
                    if (f == 0) {
                        x.setIsSelectRoot(false);
                        return false;
                    } else {
                        return true;
                    }
                }).collect(Collectors.toList());
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                Float g1= (float) 0;
                Float q1=(float) 0;
                Float g2= (float) 0;
                Float q2=(float)0;
                for (int i = 0; i < student.getCourseElectives().size(); i++) {
                    if(student.getCourseElectives().get(i).getCourse().getTeacher().getId()==teacherId){
                    g1+=student.getCourseElectives().get(i).getGrade()*student.getCourseElectives().get(i).getCourse().getValue();
                    q1+=student.getCourseElectives().get(i).getCourse().getValue();}
                }
                if(student.getCourseElectives().size()!=0)
                    g1/=q1;
                for (int i = 0; i < t1.getCourseElectives().size(); i++) {
                    if(t1.getCourseElectives().get(i).getCourse().getTeacher().getId()==teacherId){
                        q2+=t1.getCourseElectives().get(i).getCourse().getValue();
                        g2 += t1.getCourseElectives().get(i).getGrade()*t1.getCourseElectives().get(i).getCourse().getValue();
                }
                }
                if(t1.getCourseElectives().size()!=0)
                    g2/=q2;
                return g2.compareTo(g1);

            }
        });
        int WantStudentNum=teacherRepository.findById(teacherId).orElse(null).getWantStudentNum()-teacherRepository.findById(teacherId).orElse(null).getStudents().size();
        int i=0;
        for (i = 0; i < students.size()&&i<WantStudentNum; i++) {
            students.get(i).setIsSelectRoot(true);

        }
        for(;i<students.size();i++){
            students.get(i).setIsSelectRoot(false);
        }
        Student stu=null;
            for (int i1 = 0; i1 < students.size(); i1++) {
                if(students.get(i1).getId()==studentId){
                    stu=students.get(i1);
                    break;
                }
            }
        if(stu==null||stu.getIsSelectRoot()==false)return 0;
        if(stu.getTeacher()!=null) return 1;
        if(stu.getIsSelectRoot().equals(true)){

                Teacher tea=teacherRepository.findById(teacherId).orElse(null);
                if(tea.getStudents().size()<tea.getSelectStudentNum()) {
                    stu.setTeacher(tea);
                    return 2;
                }else{
                    return 0;
                }

        }
        return 2;
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 修改学生学号姓名
     * @param id
     * @param studentId
     * @param name
     * @return
     */
    public Student updateStudent(Integer id, Integer studentId, String name){
        Student stu=studentRepository.findById(id).orElse(null);
        stu.getUser().setName(name);
        stu.getUser().setNumber(studentId);
        return stu;
    }


    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByNum(Integer number) {return studentRepository.find(number);
    }
}
