package com.example.finalproject.wordle.ui.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.wordle.event.keyboard.WordleKeyboardKeyPressedEvent;
import com.example.finalproject.wordle.event.keyboard.WordleKeyboardListener;

import java.util.ArrayList;

public class WordleKeyboard
{
    private ArrayList<WordleKeyboardKey> keys;
    private ArrayList<WordleKeyboardListener> listeners;

    private AppCompatActivity activity;

    protected WordleKeyboard(AppCompatActivity activity)
    {
        this.activity = activity;
        keys = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public static WordleKeyboard build(AppCompatActivity activity)
    {
        return new WordleKeyboard(activity);
    }

    public WordleKeyboard addKey(WordleKeyboardKey key)
    {
        keys.add(key);
        return this;
    }

    public void removeKey(WordleKeyboardKey key)
    {
        keys.remove(key);
    }

    public WordleKeyboardKey getKey(char charCode)
    {
        for(WordleKeyboardKey key : keys)
        {
            if(key.getCharCode() == charCode)
                return key;
        }
        return null;
    }
    public void registerListener(WordleKeyboardListener listener)
    {
        listeners.add(listener);
    }

    public void unregisterListener(WordleKeyboardListener listener)
    {
        listeners.remove(listener);
    }

    protected void dispatchOnKeyPressedEvent(WordleKeyboardKeyPressedEvent event)
    {
        for(WordleKeyboardListener listener : listeners)
        {
            listener.onKeyPressed(event);
        }
    }

    protected AppCompatActivity activity()
    {
        return activity;
    }
}
