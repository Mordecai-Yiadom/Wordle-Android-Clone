package com.example.finalproject.wordle.game;

import java.util.ArrayList;

//Represents the active state of a wordle game
public class WordleGame
{
    public static final int DEFAULT_WORD_LENGTH = 5;
    public static final int DEFAULT_ATTEMPT_COUNT = 6;

    private int maxAttemptCount;
    private int attemptsCompleted;
    private final String wordToGuess;

    protected WordleGame(String word)
    {
        maxAttemptCount = DEFAULT_ATTEMPT_COUNT;
        StringBuilder finalWord = new StringBuilder();

        for(char c : word.toUpperCase().toCharArray())
        {
            if(Character.isAlphabetic(c))
                finalWord.append(c);
        }

        wordToGuess = finalWord.toString();
    }

    public ArrayList<CharacterStatus> submitGuess(String guess)
    {
        if(maxAttemptCount == 0 || guess.length() != wordToGuess.length()) return null;

        ArrayList<CharacterStatus> characterStatuses = new ArrayList<>();

        int i = 0;
        for(char c : guess.toCharArray())
        {
            characterStatuses.add(getCharacterStatus(c, i));
            i++;
        }

        attemptsCompleted++;
        return characterStatuses;
    }

    public int getAttemptsRemaining()
    {
        return maxAttemptCount - attemptsCompleted;
    }

    public int getAttemptsCompleted()
    {
        return attemptsCompleted;
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
