package com.example.finalproject.game;

public class WordleGameManager
{
    public WordleGame startGame(String word)
    {
        return new WordleGame(word);
    }
}
