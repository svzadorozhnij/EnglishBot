package com.example.EnglishBot.repositors;

import com.example.EnglishBot.entity.WordEntity;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<WordEntity, Long> {

}
