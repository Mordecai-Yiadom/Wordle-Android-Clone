package com.example.finalproject.wordle.ui.animation;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WordleColorFadeAnimation
{
    private ValueAnimator valueAnimator;
    private Type type;

    public WordleColorFadeAnimation(Type type, View view, long duration, int... color)
    {
        valueAnimator = ValueAnimator.ofArgb(color);
        valueAnimator.setDuration(duration);
        this.type = type;

        switch(type)
        {
            case BACKGROUND_COLOR:
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator animation)
                    {
                        view.setBackgroundColor((Integer) animation.getAnimatedValue());
                    }
                });
                break;

            case TEXT_COLOR:
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator animation)
                    {
                        if(view instanceof TextView)
                        {
                            TextView textView = (TextView) view;
                            textView.setTextColor((Integer) animation.getAnimatedValue());
                        }
                    }
                });
                break;
        }

    }

    public void setStartOffset(long startDelay)
    {
        valueAnimator.setStartDelay(startDelay);
    }

    public void start()
    {
        valueAnimator.start();
    }

    public void repeatForever()
    {
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }



    public enum Type
    {
        BACKGROUND_COLOR,
        TEXT_COLOR,
    }

}
