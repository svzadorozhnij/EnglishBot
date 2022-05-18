package com.example.EnglishBot.controller;

import com.example.EnglishBot.entity.InfoEntity;
import com.example.EnglishBot.repositors.InfoRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class InfoController {

    private static InfoRepository infoRepository;

    public InfoController(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    public InfoEntity findInfoById(@RequestParam Long id) {
        return infoRepository.findById(id).orElse(null);
    }

}
