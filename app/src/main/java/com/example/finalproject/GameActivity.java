package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.wordle.WordleGame;
import com.example.finalproject.wordle.event.keyboard.WordleKeyboardKeyPressedEvent;
import com.example.finalproject.wordle.event.keyboard.WordleKeyboardListener;
import com.example.finalproject.wordle.ui.WordleTextField;
import com.example.finalproject.wordle.ui.keyboard.WordleKeyboard;
import com.example.finalproject.wordle.ui.keyboard.WordleKeyboardKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameActivity extends AppCompatActivity implements WordleKeyboardListener
{
    private WordleGame wordleGame;
    private WordleKeyboard keyboard;

    private TextView attemptCountDebugLabel, attemptBufferDebugLabel;

    private ArrayList<WordleTextField> attemptTextFields;

    private boolean doDebugLogging = true;

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
        initTextFields();
        initKeyboard();

        if(doDebugLogging) initDebugMenu();
    }

    private void initWordleGame()
    {
        this.wordleGame = new WordleGame("LARGE");
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

    private void initDebugMenu()
    {
        attemptCountDebugLabel = findViewById(R.id.debug_AttemptCount);
        attemptBufferDebugLabel = findViewById(R.id.debug_AttemptBuffer);

        attemptCountDebugLabel.setText(String.format("AttemptCount: %d",
                wordleGame.getAttemptsRemaining()));
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

    private void submitAttempt()
    {
        WordleTextField currentAttemptTextField = getCurrentAttemptTextField();
        if(currentAttemptTextField == null) return;

        String currentAttempt = currentAttemptTextField.toString();

        ArrayList<WordleGame.CharacterStatus> charStatuses =
                wordleGame.submitGuess(currentAttempt);
        if(charStatuses == null) return;



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

            key.setBackgroundColor(backgroundColor);

            attemptTextFields.get(wordleGame.getAttemptsCompleted() - 1)
                    .setBackgroundColorAt(i, backgroundColor);
            attemptTextFields.get(wordleGame.getAttemptsCompleted() - 1)
                    .setTextColorAt(i, textColor);

//            attemptTextFields.get(wordleGame.getAttemptsCompleted() - 1)
//                    .startEmphasisAnimationAt(i, 0.2f, 700);

            attemptTextFields.get(wordleGame.getAttemptsCompleted() - 1)
                    .startElapsedAnimation(0.5f, 500, 75);
        }


        //Debug Info
        if(doDebugLogging)
        {
            attemptCountDebugLabel.setText(String.format("AttemptCount: %d",
                    wordleGame.getAttemptsRemaining()));

            attemptBufferDebugLabel.setText(String.format("AttemptBuffer: \"%s\"", currentAttempt));
        }
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

        //Debug Info
        if(doDebugLogging)
        {
            attemptBufferDebugLabel.setText(
                    String.format("AttemptBuffer: \"%s\"", currentAttemptTextField));
        }
    }

    private void removeCharFromAttempt()
    {
        WordleTextField currentAttemptTextField = getCurrentAttemptTextField();
        if(currentAttemptTextField == null) return;
        currentAttemptTextField.removeLastChar();

        //Debug Info
        if(doDebugLogging)
        {
            attemptBufferDebugLabel.setText(String.format("AttemptBuffer: \"%s\"",
                    currentAttemptTextField));
        }
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