package com.librarymknw.userService.infrastructure.adapters;

import com.librarymknw.userService.core.domain.models.User;
import com.librarymknw.userService.core.ports.UserRepositoryPort;
import com.librarymknw.userService.infrastructure.exceptions.RepositoryMBException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Repository
public class UserRepositoryMB implements UserRepositoryPort {

    private static final SqlSessionFactory sqlSessionFactory;

    static {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RepositoryMBException("Failed to initialize SqlSessionFactory", e);
        }
    }

    @Override
    public List<User> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.librarymknw.userService.infrastructure.persistence.mappers.UserMapper.findAll");
        }
    }

    @Override
    public User findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("com.librarymknw.userService.infrastructure.persistence.mappers.UserMapper.findById", id);
        }
    }

    @Override
    public void save(User user) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("com.librarymknw.userService.infrastructure.persistence.mappers.UserMapper.save", user);
            session.commit();
        }
    }

    @Override
    public void update(User user) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("com.librarymknw.userService.infrastructure.persistence.mappers.UserMapper.update", user);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("com.librarymknw.userService.infrastructure.persistence.mappers.UserMapper.delete", id);
            session.commit();
        }
    }
}
