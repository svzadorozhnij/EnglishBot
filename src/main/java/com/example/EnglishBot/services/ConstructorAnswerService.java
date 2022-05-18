package com.example.EnglishBot.services;

import com.example.EnglishBot.entity.WordEntity;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public class ConstructorAnswerService {

    public static SendMessage sendTranslate(Message message, WordEntity str,
        List<InlineKeyboardButton> arrayStr) {
        return SendMessage.builder()
            .text("Выбери правильный перевод: \n \n" + str.getName() + " " + str.getTranscription())
            .chatId(message.getChatId().toString())
            .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(arrayStr).build())
            .build();
    }

    public static SendMessage sendResultExam(
        Message message, WordEntity str, String text) {
        return SendMessage.builder()
            .text(text + " : \n \n"
                + str.getName() + " "
                + str.getTranscription() + " "
                + str.getTranslate())
            .chatId(message.getChatId().toString())
            .build();
    }


    public static EditMessageReplyMarkup addEmoji(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String[] result = callbackQuery.getData().split(":");
        setEmoji(result);
        return EditMessageReplyMarkup.builder()
            .chatId(message.getChatId().toString())
            .messageId(message.getMessageId())
            .replyMarkup(InlineKeyboardMarkup.builder()
                .keyboardRow(LearnService.addButton(LearnService.answer))
                .build())
            .build();
    }


    private static void setEmoji(String[] result) {
        for (WordEntity elem : LearnService.answer) {
            if (elem.getName().equals(result[0]) && result[1].equals("1")) {
                elem.setTranslate(elem.getTranslate() + " " + "✅");
            } else if (elem.getName().equals(result[0])) {
                elem.setTranslate(elem.getTranslate() + " " + "⛔");
            }
        }
    }

    public static SendMessage message(Message message, String text) {
        return SendMessage.builder()
            .text(text)
            .chatId(message.getChatId().toString())
            .build();
    }
}
