package com.example.finalproject.wordle.game;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.loader.ResourcesLoader;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class WordleGameManager
{
    private static ArrayList<String> WORDLE_DEFAULT_WORDS;
    private static ArrayList<String> WORDLE_HARD_WORDS;

    private WordleGameManager()
    {}
    public static WordleGame createGame(String word)
    {
        return new WordleGame(word);
    }

    public static WordleGame createRandomGame(GameMode gameMode)
    {
        int randomIndex;
        switch(gameMode)
        {
            case NORMAL:
                randomIndex = (int) (Math.random() * WORDLE_DEFAULT_WORDS.size());
                return new WordleGame(WORDLE_DEFAULT_WORDS.get(randomIndex));

            case HARD:
                randomIndex = (int) (Math.random() * WORDLE_HARD_WORDS.size());
                return new WordleGame(WORDLE_HARD_WORDS.get(randomIndex));
        }

        return null;
    }


    public static void setDefaultWords(ArrayList<String> defaultWords)
    {
        WORDLE_DEFAULT_WORDS = defaultWords;
    }

    public static void setHardWords(ArrayList<String> hardWords)
    {
        WORDLE_HARD_WORDS = hardWords;
    }

    public enum GameMode
    {
        NORMAL,
        HARD,

    }
}
