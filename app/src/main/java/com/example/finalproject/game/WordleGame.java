package com.example.finalproject.game;

import java.util.ArrayList;
import java.util.List;

//Represents the active state of a wordle game
public class WordleGame
{
    private static final int DEFAULT_WORD_LENGTH = 5;
    private static final int DEFAULT_ATTEMPT_COUNT = 6;

    private int attemptCount;
    private final String wordToGuess;

    public WordleGame(String word)
    {
        attemptCount = DEFAULT_ATTEMPT_COUNT;
        wordToGuess = word;
    }

    public ArrayList<CharacterStatus> submitGuess(String guess)
    {
        if(attemptCount == 0) return null;

        ArrayList<CharacterStatus> characterStatuses = new ArrayList<>();

        int i = 0;
        for(char c : guess.toCharArray())
        {
            characterStatuses.add(getCharacterStatus(c, i));
            i++;
        }

        attemptCount--;
        return characterStatuses;
    }

    public int getAttemptCount()
    {
        return this.attemptCount;
    }

    public String getWordToGuess()
    {
        return wordToGuess;
    }

    private CharacterStatus getCharacterStatus(char c, int index)
    {
        //Check if char is in correct position
        if(c == this.wordToGuess.charAt(index))
        {
            return CharacterStatus.PRESENT_AND_CORRECT_POSITION;
        }

        //Check if char is present at all
        for(char wordToGuessChar : wordToGuess.toCharArray())
        {
            if(c == wordToGuessChar)
                return CharacterStatus.PRESENT_BUT_INCORRECT_POSITION;
        }

        return CharacterStatus.NOT_PRESENT;
    }

    public enum CharacterStatus
    {
        PRESENT_AND_CORRECT_POSITION,
        PRESENT_BUT_INCORRECT_POSITION,
        NOT_PRESENT,
    }

}
