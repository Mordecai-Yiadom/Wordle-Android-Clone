package com.example.finalproject.wordle.game;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.loader.ResourcesLoader;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.util.ArrayList;

public class WordleGameManager
{
    private static ArrayList<String> WORDLE_DEFAULT_WORDS;
    private static boolean wordleDefaultWordsLoaded = false;

    private WordleGameManager()
    {}
    public static WordleGame createGame(String word)
    {
        return new WordleGame(word);
    }

    public static WordleGame createRandomGame()
    {
        int randomIndex = (int) (Math.random() * WORDLE_DEFAULT_WORDS.size());

        return new WordleGame(WORDLE_DEFAULT_WORDS.get(randomIndex));
    }

    public static void setDefaultWords(ArrayList<String> defaultWords)
    {
        WORDLE_DEFAULT_WORDS = defaultWords;
    }


}
