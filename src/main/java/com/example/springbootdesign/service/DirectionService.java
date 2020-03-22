package com.example.springbootdesign.service;

import com.example.springbootdesign.entity.Direction;
import com.example.springbootdesign.repository.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.time.Duration;
import java.util.List;

@Service
@Transactional
public class DirectionService {
    @Autowired
    private DirectionRepository directionRepository;

    /**
     * 添加方向
     * @param name
     */
    private void add(String name){
        Direction dir=new Direction();
        dir.setName(name);
        directionRepository.save(dir);
    }

    /**
     * 修改方向名称
     * @param id
     * @param name
     */
    private void update(Integer id,String name){
        Direction dir=directionRepository.findById(id).orElse(null);
        dir.setName(name);
    }

    /**
     * 所有的方向
     * @return
     */
    private List<Direction> list(){
        return directionRepository.list();
    }

    /**
     * 某学生选择的所有方向
     * @param id
     * @return
     */
    private List<Direction> list(Integer id){
       return directionRepository.list(id);
    }
}
