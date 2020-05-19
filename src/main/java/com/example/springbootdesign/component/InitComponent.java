package com.example.springbootdesign.component;

import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.entity.User;
import com.example.springbootdesign.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//实现初始化接口
@Component
public class InitComponent implements InitializingBean {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Override
    public void afterPropertiesSet() throws Exception {
        int num=1001;
        if(userService.getUser(num)==null){
            User user=new User();
            user.setNumber(num);
            user.setName("root");
            user.setRole(User.Role.ADMIN);
            Teacher tea=new Teacher();
            tea.setPassword(encoder.encode("root"));
            userService.addTeacher(tea, user);
        }
    }
}
