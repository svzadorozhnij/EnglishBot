package com.example.EnglishBot.controller;

import com.example.EnglishBot.entity.WordEntity;
import com.example.EnglishBot.repositors.WordRepository;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

/**
 * Class for massively adding new words to the database from a file. Not used in the project
 */

@Component
public class WordController {

    private WordRepository wordRepository;

    public WordController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public WordEntity saveWord(WordEntity word) {
        return wordRepository.save(word);
    }

    public Long sizeDB() {
        return wordRepository.count();
    }

    public ArrayList<WordEntity> findAll() {
        return (ArrayList<WordEntity>) wordRepository.findAll();
    }


    /**
     * Algorithm for massively adding new words to the database from a file. Not used in the project
     */
    public String saveAll() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/main/java/com/example/EnglishBot/word.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> wordarray = new ArrayList<>();
        ArrayList<String> transcrip = new ArrayList<>();
        ArrayList<String> translate = new ArrayList<>();
        String word = null;
        int a = 1;
        while (true) {
            try {
                if (a == 1) {
                    word = br.readLine();
                    if (word == null) break;
                    wordarray.add(word);
                    a++;
                } else if (a == 2) {
                    word = br.readLine();
                    if (word == null) break;
                    transcrip.add(word);
                    a++;
                } else if (a == 3) {
                    word = br.readLine();
                    if (word == null) break;
                    translate.add(word);
                    a = 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < wordarray.size() - 1; i++) {
            saveWord(new WordEntity(wordarray.get(i), transcrip.get(i), translate.get(i)));
        }
        return "save " + wordarray.size() + " word";
    }
}
