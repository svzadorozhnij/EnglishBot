package com.example.EnglishBot.services;

import com.example.EnglishBot.controller.UserController;
import com.example.EnglishBot.controller.WordController;
import com.example.EnglishBot.entity.UserEntity;
import com.example.EnglishBot.entity.WordEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public class LearnService {

    private WordController wordController;
    private UserController userController;

    public static WordEntity learnWord;
    public static List<WordEntity> answer = new ArrayList<>();

    public LearnService(WordController wordController,
        UserController userController) {
        this.wordController = wordController;
        this.userController = userController;
    }

    public SendMessage newWord(Message message) {
        UserEntity user = userController.findById(message.getChatId());
        ArrayList<WordEntity> libraryWord = wordController.findAll();
        if (user.getStudiedStr() == null) {
            learnWord = libraryWord.get((int) (Math.random() * libraryWord.size()));
            user.setStudiedStr(learnWord.getId().toString());
        } else {
            ArrayList<String> name = new ArrayList<>();
            Collections.addAll(name,user.getStudiedStr().split(";"));
            do {
                learnWord = libraryWord.get((int) (Math.random() * libraryWord.size()));
            } while (name.contains(learnWord.getId().toString()));

            user.setStudiedStr(user.getStudiedStr() + ";" + learnWord.getId().toString());
        }
        userController.updateStudied(user);
        translOptions(libraryWord);
        return ConstructorAnswerService.sendTranslate(message, learnWord, addButton(answer));
    }


    public static List<InlineKeyboardButton> addButton(List<WordEntity> answer) {
        List<InlineKeyboardButton> arrayStr = new ArrayList<>(3);
        for (WordEntity word : answer) {
            arrayStr.add(InlineKeyboardButton.builder()
                .text(word.getTranslate())
                .callbackData(
                        word.getName()
                        + ":"
                        + ((word.getName().equalsIgnoreCase(learnWord.getName())) ? "1" : "0"))
                .build());
        }
        return arrayStr;
    }


    private void translOptions(ArrayList<WordEntity> libraryWord) {
        if (!answer.isEmpty()) answer.clear();
        Random randomGenerator = new Random();
        int a = randomGenerator.nextInt(0, 3);
        List<WordEntity> y = new ArrayList<>(libraryWord.stream()
            .filter(x -> !answer.contains(x) && x != learnWord)
            .filter(x -> x.getName().length() + 2 >= learnWord.getName().length())
            .filter(x -> x.getName().length() - 2 <= learnWord.getName().length())
            .collect(Collectors.toList()));
        for (int i = 0; i < 3; i++) {
            if (i == a) {
                answer.add(learnWord);
            } else {
                answer.add(y.get((int) (Math.random() * y.size())));
            }
        }
    }


    public SendMessage trainWord(Message message) {
        UserEntity user = userController.findById(message.getChatId());
        ArrayList<WordEntity> word = wordController.findAll();
        HashMap<Long, Integer> studied = user.getStudied();
        WordEntity wordFirst = word.stream()
            .filter(studied::containsKey)
            .findAny()
            .get();
        return SendMessage
            .builder()
            .chatId(String.valueOf(message.getChatId()))
            .text(wordFirst.getName() + " " + wordFirst.getTranslate()).build();
    }


    public SendMessage checkAnswer(CallbackQuery callbackQuery) {
        String[] result = callbackQuery.getData().split(":");
        if (result[1].equalsIgnoreCase("1")) {
            return ConstructorAnswerService.sendResultExam(
                callbackQuery.getMessage(),
                learnWord,
                "Success");
        } else {
            return ConstructorAnswerService.sendResultExam(
                callbackQuery.getMessage(),
                learnWord,
                " Not successful\nCorrect answer");
        }
    }
}
