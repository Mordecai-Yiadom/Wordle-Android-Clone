package com.example.finalproject;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.wordle.game.WordleGame;
import com.example.finalproject.wordle.event.keyboard.WordleKeyboardKeyPressedEvent;
import com.example.finalproject.wordle.event.keyboard.WordleKeyboardListener;
import com.example.finalproject.wordle.game.WordleGameManager;
import com.example.finalproject.wordle.ui.WordleTextField;
import com.example.finalproject.wordle.ui.animation.WordleColorFadeAnimation;
import com.example.finalproject.wordle.ui.animation.WordleEmphasisAnimation;
import com.example.finalproject.wordle.ui.keyboard.WordleKeyboard;
import com.example.finalproject.wordle.ui.keyboard.WordleKeyboardKey;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements WordleKeyboardListener
{
    private WordleGame wordleGame;
    private WordleKeyboard keyboard;

    private ArrayList<WordleTextField> attemptTextFields;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWordleGame();
        initToolbar();
        initTextFields();
        initKeyboard();
        initWinMessage();
    }

    private void initWordleGame()
    {
        this.wordleGame = WordleGameManager.createRandomGame();
    }
    private void initKeyboard()
    {
        keyboard = WordleKeyboard.build(this);

        //Keyboard Row 1
        keyboard.addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.Q, R.id.keyboard_Q))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.W, R.id.keyboard_W))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.E, R.id.keyboard_E))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.R, R.id.keyboard_R))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.T, R.id.keyboard_T))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.Y, R.id.keyboard_Y))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.U, R.id.keyboard_U))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.I, R.id.keyboard_I))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.O, R.id.keyboard_O))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.P, R.id.keyboard_P))

                //Keyboard Row 2
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.A, R.id.keyboard_A))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.S, R.id.keyboard_S))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.D, R.id.keyboard_D))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.F, R.id.keyboard_F))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.G, R.id.keyboard_G))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.H, R.id.keyboard_H))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.J, R.id.keyboard_J))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.K, R.id.keyboard_K))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.L, R.id.keyboard_L))

                //Keyboard Row 3
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.ENTER, R.id.keyboard_ENTER))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.Z, R.id.keyboard_Z))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.X, R.id.keyboard_X))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.C, R.id.keyboard_C))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.V, R.id.keyboard_V))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.B, R.id.keyboard_B))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.N, R.id.keyboard_N))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.M, R.id.keyboard_M))
                .addKey(new WordleKeyboardKey(keyboard, WordleKeyboardKey.WordleCharCode.BACKSPACE, R.id.keyboard_BACKSPACE));


        keyboard.registerListener(this);
    }


    private void initTextFields()
    {
        attemptTextFields = new ArrayList<>();
        attemptTextFields.add(WordleTextField.create(WordleTextField.Row.ROW_0, 5,this));
        attemptTextFields.add(WordleTextField.create(WordleTextField.Row.ROW_1, 5,this));
        attemptTextFields.add(WordleTextField.create(WordleTextField.Row.ROW_2, 5,this));
        attemptTextFields.add(WordleTextField.create(WordleTextField.Row.ROW_3, 5,this));
        attemptTextFields.add(WordleTextField.create(WordleTextField.Row.ROW_4, 5,this));
        attemptTextFields.add(WordleTextField.create(WordleTextField.Row.ROW_5, 5,this));
    }

    private void initToolbar()
    {
        ImageButton homeButton = findViewById(R.id.homeButton);
        if(homeButton == null) return;

        homeButton.setOnClickListener((View view)->
        {
            WordleEmphasisAnimation emphasisAnimation = new WordleEmphasisAnimation(homeButton,
                    0.5f, 1000, 120.0f, 50.0f);

            view.startAnimation(emphasisAnimation);
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button gamemodeButton = findViewById(R.id.gamemodeButton);
        if(gamemodeButton == null) return;




    }

    private void initWinMessage()
    {
        TextView winMessageView = findViewById(R.id.winMessage);
        if(winMessageView == null) return;

        winMessageView.setVisibility(View.INVISIBLE);
    }
    private boolean checkAttemptIsCorrect(ArrayList<WordleGame.CharacterStatus> characterStatuses)
    {
        for(WordleGame.CharacterStatus status : characterStatuses)
        {
            if(status != WordleGame.CharacterStatus.PRESENT_AND_CORRECT_POSITION)
                return false;
        }
        return true;
    }
    private void submitAttempt()
    {
        WordleTextField currentAttemptTextField = getCurrentAttemptTextField();
        if(currentAttemptTextField == null) return;

        String currentAttempt = currentAttemptTextField.toString();

        ArrayList<WordleGame.CharacterStatus> charStatuses =
                wordleGame.submitGuess(currentAttempt);
        if(charStatuses == null) return;


        long colorFadeStartOffset = 0;

        for(int i = 0; i < currentAttempt.length(); i++)
        {
            WordleKeyboardKey key = keyboard.getKey(currentAttempt.charAt(i));
            //if(key == null) continue;

            int backgroundColor = getColor(R.color.white);
            int textColor = getColor(R.color.white);

            switch(charStatuses.get(i))
            {
                case PRESENT_AND_CORRECT_POSITION:
                    backgroundColor = getColor(R.color.wordle_green);
                    break;

                case PRESENT_BUT_INCORRECT_POSITION:
                    backgroundColor = getColor(R.color.wordle_yellow);
                    break;

                case NOT_PRESENT:
                    backgroundColor = getColor(R.color.wordle_gray);
                    break;
            }

            //Set Background colors and animate the text fields
            key.setBackgroundColor(backgroundColor);
            WordleTextField textField = attemptTextFields.get(wordleGame.getAttemptsCompleted() - 1);

            textField.setTextColorAt(i, textColor);
            textField.startElapsedColorFadeAnimationAt(i, backgroundColor, 750, colorFadeStartOffset);
            colorFadeStartOffset += 75;

            textField.startElapsedAnimation(0.5f, 500, 75, backgroundColor);
        }

        //Checks if game has been won or lost
        if(checkAttemptIsCorrect(charStatuses))
            displayWin();

        else if(!wordleGame.hasAttemptsRemaining())
            displayLose();

    }

    private void addCharToAttempt(char c)
    {
        WordleTextField currentAttemptTextField = getCurrentAttemptTextField();
        if(currentAttemptTextField == null) return;
        currentAttemptTextField.append(c);


        currentAttemptTextField.startEmphasisAnimationAt(
                currentAttemptTextField.getText().length() - 1,
                0.2f,
                250);
    }

    private void removeCharFromAttempt()
    {
        WordleTextField currentAttemptTextField = getCurrentAttemptTextField();
        if(currentAttemptTextField == null) return;
        currentAttemptTextField.removeLastChar();

        currentAttemptTextField.startEmphasisAnimationAt(
                currentAttemptTextField.getText().length(),
                -0.2f,
                250);
    }

    private WordleTextField getCurrentAttemptTextField()
    {
        if(wordleGame == null) return null;
        return attemptTextFields.get(wordleGame.getAttemptsCompleted());
    }

    private WordleTextField getPreviousAttemptTextField()
    {
        if(wordleGame == null) return null;
        return attemptTextFields.get(wordleGame.getAttemptsCompleted() - 1);
    }

    private void displayWin()
    {
        hideKeyboard();
        hideToolbar();
        startWinAnimation();
        displayWinMessage("YOU WIN!",
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.CYAN,
                Color.BLUE,
                Color.MAGENTA);

        ValueAnimator timer = ValueAnimator.ofArgb(Color.RED, Color.GREEN);
        timer.setDuration(4000);
        timer.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                transitionHome();
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }
        });

        timer.start();

    }

    private void displayLose()
    {
        hideKeyboard();
        hideToolbar();
        startLoseAnimation();
        String message = String.format("the word was \"%s\"", wordleGame.getWordToGuess());

        displayWinMessage(message,
                getColor(R.color.wordle_red),
                getColor(R.color.wordle_dark_red),
                getColor(R.color.wordle_red));

        ValueAnimator timer = ValueAnimator.ofArgb(Color.RED, Color.GREEN);
        timer.setDuration(5500);
        timer.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                transitionHome();
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }
        });

        timer.start();
    }


    private void startWinAnimation()
    {
        View mainView = findViewById(R.id.main);
        WordleColorFadeAnimation colorFadeAnimation
                = new WordleColorFadeAnimation(WordleColorFadeAnimation.Type.BACKGROUND_COLOR,
                mainView,
                1000,
                Color.GREEN);

        colorFadeAnimation.start();

        View attemptContainer = findViewById(R.id.attemptContainer);

        ValueAnimator translateAnimator
                = ValueAnimator.ofFloat(attemptContainer.getTranslationY(),
                attemptContainer.getTranslationY() - 2000);
        translateAnimator.setInterpolator(new AccelerateInterpolator(1f));

        translateAnimator.setDuration(1000);
        translateAnimator.setStartDelay(500);

        translateAnimator.addUpdateListener((animator)->
        {
            attemptContainer.setTranslationY((float)animator.getAnimatedValue());
        });

        translateAnimator.start();
    }

    private void startLoseAnimation()
    {
        View view = findViewById(R.id.main);
        WordleColorFadeAnimation colorFadeAnimation
                = new WordleColorFadeAnimation(WordleColorFadeAnimation.Type.BACKGROUND_COLOR,
                view,
                1000,
                Color.RED);

        View attemptContainer = findViewById(R.id.attemptContainer);

        ValueAnimator translateAnimator
                = ValueAnimator.ofFloat(attemptContainer.getTranslationY(),
                attemptContainer.getTranslationY() - 2000);
        translateAnimator.setInterpolator(new AccelerateInterpolator(1f));

        translateAnimator.setDuration(1000);
        translateAnimator.setStartDelay(500);

        translateAnimator.addUpdateListener((animator)->
        {
            attemptContainer.setTranslationY((float)animator.getAnimatedValue());
        });

        translateAnimator.start();
        colorFadeAnimation.start();

    }

    private void displayWinMessage(String message, int... colors)
    {
        TextView winMessageView = findViewById(R.id.winMessage);
        if(winMessageView == null) return;

        winMessageView.setVisibility(View.VISIBLE);

        winMessageView.setText(message);

        WordleColorFadeAnimation colorFadeAnimation
                = new WordleColorFadeAnimation(WordleColorFadeAnimation.Type.TEXT_COLOR,
                winMessageView,
                3000,
                colors);

        //Translation animation
        ValueAnimator translateAnimator
                = ValueAnimator.ofFloat(winMessageView.getTranslationY(),
                winMessageView.getTranslationY() - 1000);
        translateAnimator.setInterpolator(new AccelerateInterpolator(1f));
        translateAnimator.setDuration(700);
        translateAnimator.setStartDelay(200);
        translateAnimator.addUpdateListener((animation)->
        {
            winMessageView.setTranslationY((float)animation.getAnimatedValue());
        });

        //Alpha animation
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);

        //Emphasis Animation
        WordleEmphasisAnimation emphasisAnimation
                = new WordleEmphasisAnimation(winMessageView,
                0.5f,
                1000,
                320,
                50);
        emphasisAnimation.setStartOffset(700);


        //Animation set (both emphasis and alpha animations)
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(emphasisAnimation);

        winMessageView.startAnimation(animationSet);

        colorFadeAnimation.repeatForever();
        colorFadeAnimation.start();
        translateAnimator.start();
    }

    private void hideKeyboard()
    {
        View keyboardView = findViewById(R.id.keyboard);
        ValueAnimator translateAnimator
                = ValueAnimator.ofFloat(keyboardView.getTranslationY(),
                keyboardView.getTranslationY() + 2000);
        translateAnimator.setInterpolator(new AccelerateInterpolator(1f));

        translateAnimator.setDuration(1000);
        translateAnimator.setStartDelay(500);

        translateAnimator.addUpdateListener((animator)->
        {
            keyboardView.setTranslationY((float)animator.getAnimatedValue());
        });

        translateAnimator.start();
    }

    private void hideToolbar()
    {
        View toolbarView = findViewById(R.id.toolbar);
        ValueAnimator translateAnimator
                = ValueAnimator.ofFloat(toolbarView.getTranslationY(),
                toolbarView.getTranslationY() - 2000);
        translateAnimator.setInterpolator(new AccelerateInterpolator(1f));

        translateAnimator.setDuration(1000);
        translateAnimator.setStartDelay(500);

        translateAnimator.addUpdateListener((animator)->
        {
            toolbarView.setTranslationY((float)animator.getAnimatedValue());
        });

        translateAnimator.start();
    }

    private void transitionHome()
    {
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onKeyPressed(WordleKeyboardKeyPressedEvent event)
    {
        WordleKeyboardKey key = event.getKeyPressed();
        WordleKeyboardKey.WordleCharCode charCode =
                WordleKeyboardKey.WordleCharCode.getCharCode(key.getCharCode());

        if(charCode == null) return;

        switch(charCode)
        {
            case ENTER:
                submitAttempt();
                break;

            case BACKSPACE:
                removeCharFromAttempt();
                break;

            default:
                addCharToAttempt(charCode.value());
                break;
        }
    }
}