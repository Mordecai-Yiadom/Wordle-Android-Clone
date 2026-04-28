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

import com.example.finalproject.wordle.game.WordleGameManager;
import com.example.finalproject.wordle.ui.animation.WordleEmphasisAnimation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

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

        //Load wordle words TXT file
        WordleGameManager.setDefaultWords(loadTextFile(R.raw.wordle_default_words));


        //Init Homepage activity UI members
        initPlayButton();
    }

    private void initPlayButton()
    {
        playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener((view)->
        {
            WordleEmphasisAnimation emphasisAnimation = new WordleEmphasisAnimation(playButton,
                    2, 1000, 120.0f, 50.0f);

            view.startAnimation(emphasisAnimation);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });


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


}

