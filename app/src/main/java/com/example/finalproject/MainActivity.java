package com.example.finalproject;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.icu.number.Scale;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Init Homepage activity UI members
        initPlayButton();
    }

    private void initPlayButton()
    {
        playButton = findViewById(R.id.playButton);

//        TranslateAnimation translateAnimation1 = new TranslateAnimation(0,100, 0, 100);
//        translateAnimation1.setDuration(2000);
//        animation.setDuration(1000);


        ScaleAnimation scaleAnimation = new ScaleAnimation(playButton.getScaleX(), playButton.getScaleX() + 2,
                playButton.getScaleY(), playButton.getScaleY() + 2, 120.0f, 50.0f);
        scaleAnimation.setDuration(1000);


        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 120, 50);
        rotateAnimation.setDuration(1000);

        CycleInterpolator cycleInterpolator = new CycleInterpolator(0.5f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(cycleInterpolator);

        animationSet.addAnimation(scaleAnimation);
        //animationSet.addAnimation(rotateAnimation);


        playButton.setOnClickListener((view)->
        {
            view.startAnimation(animationSet);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });


    }




}

