package com.example.EnglishBot.services;

import com.example.EnglishBot.controller.InfoController;
import com.example.EnglishBot.controller.UserController;
import com.example.EnglishBot.entity.UserEntity;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class NewUser {

    private UserController userController;
    private InfoController infoController;

    @Autowired
    public NewUser(UserController userController, InfoController infoController) {
        this.userController = userController;
        this.infoController = infoController;
    }

    public SendMessage addAndSendInfo(Message message) {
        addNewUser(message.getFrom().getUserName(), message.getChatId(),
            Date.from(Instant.ofEpochSecond(message.getDate())));
        return ConstructorAnswerService.message(message,"testText");
    }

    public void addNewUser(@RequestParam String name, @RequestParam Long id, @RequestParam Date date) {
        UserEntity user = new UserEntity(id,name, date);
        userController.saveUser(user);
    }
}
