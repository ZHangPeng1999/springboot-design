package com.example.springbootdesign.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
public class EntityTest {
    @Autowired
    private EntityManager entityManager;
    @Test
    public void test_student(){
        Student stu=new Student();
        stu.setId(2017214215);
        stu.setName("张澎");
        entityManager.persist(stu);
    }
    @Test
    public void test_teacher(){
        Teacher tea=new Teacher();
        tea.setName("BO");
        entityManager.persist(tea);
    }
    @Test
    public void test_course(){
        Course cou = new Course();
        cou.setName("java");
        entityManager.persist(cou);
    }
}
