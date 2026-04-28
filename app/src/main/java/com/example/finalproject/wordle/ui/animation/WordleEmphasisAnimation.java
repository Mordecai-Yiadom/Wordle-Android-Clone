package com.example.finalproject.wordle.ui.animation;

import android.icu.number.Scale;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;

public class WordleEmphasisAnimation extends ScaleAnimation
{
    public WordleEmphasisAnimation(View view, float intensity, long duration, float pivotX, float pivotY)
    {
        super(view.getScaleX(),
                view.getScaleX() + intensity,
                view.getScaleY(),
                view.getScaleY() + intensity,
                pivotX,
                pivotY);

        setDuration(duration);
        setInterpolator(new CycleInterpolator(0.5f));

    }

    public WordleEmphasisAnimation(View view, float intensity, long duration, long startOffset, float pivotX, float pivotY)
    {
        super(view.getScaleX(),
                view.getScaleX() + intensity,
                view.getScaleY(),
                view.getScaleY() + intensity,
                pivotX,
                pivotY);

        setDuration(duration);
        setInterpolator(new CycleInterpolator(0.5f));
        setStartOffset(startOffset);
    }



}
