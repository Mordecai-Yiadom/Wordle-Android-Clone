package com.example.finalproject.wordle.ui;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.wordle.ui.animation.WordleEmphasisAnimation;

import java.util.ArrayList;

public class WordleTextField
{
    private ArrayList<TextView> textViews;
    private StringBuilder textBuffer;
    private int maxLength;

    private WordleTextField(int maxLength)
    {
        textViews = new ArrayList<>();
        textBuffer = new StringBuilder();
        this.maxLength = maxLength;
    }

    public static WordleTextField create(Row row, int maxLength, AppCompatActivity activity)
    {
        if(activity == null || row == null) return null;

        WordleTextField textField = new WordleTextField(maxLength);
        for(int id : row.columnIds)
        {
            textField.textViews.add(activity.findViewById(id));
        }
        return textField;
    }

    protected TextView getColumn(int index)
    {
        return textViews.get(index);
    }
    public void setBackgroundColorAt(int index, int color)
    {
        TextView column = textViews.get(index);
        if(column != null) column.setBackgroundColor(color);
    }

    public void setTextColorAt(int index, int color)
    {
        TextView column = textViews.get(index);
        if(column != null) column.setTextColor(color);
    }

    public void setBackgroundColor(int color)
    {
        for(TextView textView : textViews)
        {
            textView.setBackgroundColor(color);
        }
    }

    public void setTextColor(int color)
    {
        for(TextView textView : textViews)
        {
            textView.setTextColor(color);
        }
    }

    public String getText()
    {
        return textBuffer.toString();
    }

    public void setText(String text)
    {
        if(textBuffer != null)
            textBuffer = new StringBuilder(text);
    }

    public void setCharAt(int index, char c)
    {
        if(index > -1 && index < maxLength)
            textBuffer.setCharAt(index, c);
        updateTextViews();
    }

    public char getCharAt(int index)
    {
        return textBuffer.charAt(index);
    }

    public void append(char c)
    {
        if(textBuffer.length() < maxLength)
        {
            textBuffer.append(c);
            textViews.get(textBuffer.length() - 1).setText(Character.toString(c));
        }
    }

    public void removeCharAt(int index)
    {
        textBuffer.deleteCharAt(index);
    }

    public void removeLastChar()
    {
        if(textBuffer.isEmpty()) return;
        textBuffer.setLength(textBuffer.length() - 1);
        textViews.get(textBuffer.length()).setText("");
    }


    public void startEmphasisAnimationAt(int index, float intensity, long duration)
    {
        TextView column = textViews.get(index);
        if(column == null) return;

        column.startAnimation(new WordleEmphasisAnimation(column,
                intensity,
                duration,
                100,
                100));
    }

    public void startElapsedAnimation(float intensity, long duration, long elapseRate)
    {
        long startOffset = 0;
        for(TextView column : textViews)
        {
            WordleEmphasisAnimation animation = new WordleEmphasisAnimation(column,
                    intensity,
                    duration,
                    100,
                    100);

            animation.setStartOffset(startOffset);
            startOffset += elapseRate;

            column.startAnimation(animation);
        }
    }

    private void updateTextViews()
    {
        for(int i = 0; i < maxLength; i++)
        {
            if(i < textBuffer.length())
                textViews.get(i).setText(textBuffer.charAt(i));
            else
                textViews.get(i).setText("");
        }
    }

    @Override
    public String toString()
    {
        return textBuffer.toString();
    }


    public enum Row
    {
        ROW_0(R.id.attemptContainer_row1_char1, R.id.attemptContainer_row1_char2,
                R.id.attemptContainer_row1_char3, R.id.attemptContainer_row1_char4,
                R.id.attemptContainer_row1_char5),
        ROW_1(R.id.attemptContainer_row2_char1, R.id.attemptContainer_row2_char2,
                R.id.attemptContainer_row2_char3, R.id.attemptContainer_row2_char4,
                R.id.attemptContainer_row2_char5),
        ROW_2(R.id.attemptContainer_row3_char1, R.id.attemptContainer_row3_char2,
                R.id.attemptContainer_row3_char3, R.id.attemptContainer_row3_char4,
                R.id.attemptContainer_row3_char5),
        ROW_3(R.id.attemptContainer_row4_char1, R.id.attemptContainer_row4_char2,
                R.id.attemptContainer_row4_char3, R.id.attemptContainer_row4_char4,
                R.id.attemptContainer_row4_char5),
        ROW_4(R.id.attemptContainer_row5_char1, R.id.attemptContainer_row5_char2,
                R.id.attemptContainer_row5_char3, R.id.attemptContainer_row5_char4,
                R.id.attemptContainer_row5_char5),
        ROW_5(R.id.attemptContainer_row6_char1, R.id.attemptContainer_row6_char2,
                R.id.attemptContainer_row6_char3, R.id.attemptContainer_row6_char4,
                R.id.attemptContainer_row6_char5);

        private ArrayList<Integer> columnIds = new ArrayList<>();
        Row(int... columnIds)
        {
            for(int id : columnIds)
            {
                this.columnIds.add(id);
            }
        }
    }

}
