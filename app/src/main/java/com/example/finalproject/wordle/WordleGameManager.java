package com.example.finalproject.wordle;

public class WordleGameManager
{
    public WordleGame startGame(String word)
    {
        return new WordleGame(word);
    }
}
