package com.example.springbootdesign.repository.Impl;

import com.example.springbootdesign.repository.BaseRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class BaseRepositoryImpl<T,ID> extends SimpleJpaRepository<T,ID> implements BaseRepository<T,ID> {
    private EntityManager entityManager;
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager=entityManager;
    }

    @Override
    public void refresh(T t) {
        entityManager.refresh(t);
    }
}
