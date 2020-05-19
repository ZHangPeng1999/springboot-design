package com.example.springbootdesign;

import com.example.springbootdesign.repository.Impl.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)//告诉springboot使用自己实现的jpa容器s实现类

public class SpringbootDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDesignApplication.class, args);
    }
//  对密码进行编码
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
