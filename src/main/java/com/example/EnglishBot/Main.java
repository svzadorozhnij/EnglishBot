package com.example.EnglishBot;

import com.example.EnglishBot.controller.WordController;
import com.example.EnglishBot.services.ConstructorAnswerService;
import com.example.EnglishBot.services.LearnService;
import com.example.EnglishBot.services.NewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Main extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.taken}")
    private String token;
    @Autowired
    private NewUser newUser;
    private WordController wordController;
    private LearnService learnService;
    private String commandNow;

    public Main(WordController wordController, NewUser newUser, LearnService learnService) {
        this.newUser = newUser;
        this.wordController = wordController;
        this.learnService = learnService;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleAnswer(update.getCallbackQuery());
        }
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    private void handleAnswer(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        if (message.hasText()) {
            try {
                execute(ConstructorAnswerService.addEmoji(callbackQuery));
                execute(learnService.checkAnswer(callbackQuery));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        message.setText(commandNow);
        handleMessage(message);
    }

    private void handleMessage(Message message) {
        try {
            execute(checkSMS(message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public SendMessage checkSMS(Message message) {
        commandNow = message.getText();
        switch (message.getText()) {
            case "/start":
                return newUser.addAndSendInfo(message);
            case "/learn_new_word":
                return learnService.newWord(message);
            case "/practice_past_words":
                return learnService.trainWord(message);
            default:
                return SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .text("hello")
                    .build();
        }
    }
}
