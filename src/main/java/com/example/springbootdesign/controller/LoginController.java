package com.example.springbootdesign.controller;

import antlr.Token;
import com.example.springbootdesign.component.EncryptComponent;
import com.example.springbootdesign.component.MyToken;
import com.example.springbootdesign.component.RequestComponent;
import com.example.springbootdesign.entity.Teacher;
import com.example.springbootdesign.entity.User;
import com.example.springbootdesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EncryptComponent encryptComponent;
    @Value("${my.teacher}")
    private  String roleTeacher;
    @Value("${my.student}")
    private  String roleStudent;
    @Value("${my.admin}")
    private  String roleAdmin;

    @PostMapping("login")
    public Map login(@RequestBody Map<String,String> login, HttpServletResponse response){
        Teacher teacher= Optional.ofNullable(userService.getTeacherByNum(Integer.valueOf(login.get("number"))))
                .filter(u->passwordEncoder.matches(login.get("password"),u.getPassword()))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"用户名或密码错误"));
        MyToken myToken=new MyToken(teacher.getId(),teacher.getUser().getRole());
        String auth=encryptComponent.encryptToken(myToken);
        response.setHeader(MyToken.AUTHORIZATION, auth);
//        token中包含用户身份 但是前端不能通过解密token得到身份 因此通过编码用户身份传递给前端 编码后传递给前端增加安全性
        String roleCode=teacher.getUser().getRole()==User.Role.TEACHER?roleTeacher:roleAdmin;
//        KeyPairGenerator.getInstance("RSA").generateKeyPair(); 获得密钥对
        return Map.of("role",roleCode);
    }
}
