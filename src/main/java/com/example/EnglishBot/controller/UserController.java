package com.example.EnglishBot.controller;

import com.example.EnglishBot.entity.UserEntity;
import com.example.EnglishBot.repositors.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserEntity user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            saveUser(user);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserEntity> findAll() {
        return (List<UserEntity>) userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateStudied(UserEntity user) {
        deleteUser(user.getId());
        saveUser(user);
    }
}
