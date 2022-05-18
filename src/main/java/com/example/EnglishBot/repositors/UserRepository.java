package com.example.EnglishBot.repositors;

import com.example.EnglishBot.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
