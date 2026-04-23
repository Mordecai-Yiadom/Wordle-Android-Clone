package com.example.finalproject.wordle.event.keyboard;

import com.example.finalproject.wordle.ui.keyboard.WordleKeyboard;
import com.example.finalproject.wordle.ui.keyboard.WordleKeyboardKey;

public class WordleKeyboardKeyPressedEvent extends WordleKeyboardEvent
{
    private WordleKeyboardKey keyPressed;

    public WordleKeyboardKeyPressedEvent(WordleKeyboard keyboard, WordleKeyboardKey keyPressed)
    {
        super(keyboard);
        this.keyPressed = keyPressed;
    }

    public WordleKeyboardKey getKeyPressed()
    {
        return this.keyPressed;
    }
}
