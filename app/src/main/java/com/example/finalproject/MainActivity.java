package com.example.finalproject;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.icu.number.Scale;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Visibility;
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

import com.example.finalproject.wordle.game.WordleGameManager;
import com.example.finalproject.wordle.sensor.LightSensor;
import com.example.finalproject.wordle.ui.animation.WordleColorFadeAnimation;
import com.example.finalproject.wordle.ui.animation.WordleEmphasisAnimation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
{
    private Button playButton;
    private LightSensor lightSensor;

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


        initLightSensor();

        //Load wordle default words TXT file
        WordleGameManager.setDefaultWords(loadTextFile(R.raw.wordle_default_words));

        //Load wordle hard words TXT file
        WordleGameManager.setHardWords(loadTextFile(R.raw.wordle_hard_words));

        //Init Homepage activity UI members
        initPlayButton();

        //Starts the color fading animation
        startColorFadingAnimation();
    }

    private void initLightSensor()
    {
        if(lightSensor == null)
            lightSensor = new LightSensor(this);
    }

    private void initPlayButton()
    {
        playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener((view)->
        {
            WordleEmphasisAnimation emphasisAnimation = new WordleEmphasisAnimation(playButton,
                    2, 1000, 120.0f, 50.0f);
            emphasisAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    transitionToGameActivity();
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }

                @Override
                public void onAnimationStart(Animation animation)
                {

                }
            });
            view.startAnimation(emphasisAnimation);
        });
    }
    private void transitionToGameActivity()
    {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);

        lightSensor.destroy();
        lightSensor = null;
    }

    private InputStream loadRawFileResource(int resourceId)
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getBaseContext().getResources().openRawResource(resourceId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return inputStream;
    }

    private ArrayList<String> loadTextFile(int textFileId)
    {
        Scanner scanner = new Scanner(loadRawFileResource(textFileId));

        ArrayList<String> fileLines = new ArrayList<>();
        while(scanner.hasNextLine())
        {
            fileLines.add(scanner.nextLine());
        }

        return fileLines;
    }

    private void startColorFadingAnimation()
    {
        WordleColorFadeAnimation colorFadeAnimation
                = new WordleColorFadeAnimation(WordleColorFadeAnimation.Type.BACKGROUND_COLOR,
                findViewById(R.id.main),
                20000,

                getColor(R.color.white),
                getColor(R.color.wordle_yellow),
                getColor(R.color.wordle_yellow),

                getColor(R.color.white),
                getColor(R.color.wordle_gray),
                getColor(R.color.wordle_gray),

                getColor(R.color.white),
                getColor(R.color.wordle_green),
                getColor(R.color.wordle_green),

                getColor(R.color.white));

        colorFadeAnimation.repeatForever();
        colorFadeAnimation.setStartOffset(1000);
        colorFadeAnimation.start();
    }


}

