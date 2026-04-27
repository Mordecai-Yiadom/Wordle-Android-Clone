package com.example.finalproject.wordle.ui.keyboard;

import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.wordle.event.keyboard.WordleKeyboardKeyPressedEvent;

public class WordleKeyboardKey
{
    private View.OnClickListener onClickListener;
    private Button button;
    private AppCompatActivity activity;
    private char charCode;

    private WordleKeyboard keyboard;

    protected WordleKeyboardKey(WordleKeyboard keyboard, char charCode, int buttonId)
    {
        this.keyboard = keyboard;
        this.charCode = charCode;
        this.activity = keyboard.activity();

        this.button = activity.findViewById(buttonId);
        this.onClickListener = (View view)->
        {
            ScaleAnimation scaleAnimation = new ScaleAnimation(view.getScaleX(), view.getScaleX() + 0.3f,
                    view.getScaleY(), view.getScaleY() + 0.2f, 50, 50);
            scaleAnimation.setDuration(100);

            CycleInterpolator cycleInterpolator = new CycleInterpolator(0.5f);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setInterpolator(cycleInterpolator);
            animationSet.addAnimation(scaleAnimation);

            view.startAnimation(animationSet);

            this.keyboard.dispatchOnKeyPressedEvent(
                    new WordleKeyboardKeyPressedEvent(this.keyboard, this));
        };

        this.button.setOnClickListener(this.onClickListener);
    }

    public WordleKeyboardKey(WordleKeyboard keyboard, WordleCharCode code, int buttonId)
    {
        this(keyboard, code.value(), buttonId);
    }

    public void setBackgroundColor(int color)
    {
        button.setBackgroundColor(color);
    }

    public void setTextColor(int color)
    {
        button.setTextColor(color);
    }

    public char getCharCode()
    {
        return charCode;
    }

    public enum WordleCharCode
    {
        Q('Q'), W('W'), E('E'), R('R'), T('T'), Y('Y'), U('U'), I('I'), O('O'), P('P'),
        A('A'), S('S'), D('D'), F('F'), G('G'), H('H'), J('J'), K('K'), L('L'),
        Z('Z'), X('X'), C('C'), V('V'), B('B'), N('N'), M('M'),
        ENTER('\r'),
        BACKSPACE('\b');

        private char charCode;

        WordleCharCode(char c)
        {
            this.charCode = c;
        }

        public char value()
        {
            return this.charCode;
        }

        public static WordleCharCode getCharCode(char c)
        {
            for(WordleCharCode code : WordleCharCode.values())
            {
                if(code.value() == c) return code;
            }
            return null;
        }
    }
}
