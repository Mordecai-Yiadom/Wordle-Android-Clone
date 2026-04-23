package com.example.finalproject.wordle.event.keyboard;

import com.example.finalproject.wordle.ui.keyboard.WordleKeyboard;

public abstract class WordleKeyboardEvent
{
    private WordleKeyboard keyboard;

    protected WordleKeyboardEvent(WordleKeyboard keyboard)
    {
        this.keyboard = keyboard;
    }
}
