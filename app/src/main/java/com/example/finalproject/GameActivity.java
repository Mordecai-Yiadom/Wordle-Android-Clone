package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.wordle.WordleGame;
import com.example.finalproject.wordle.ui.WordleTextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameActivity extends AppCompatActivity
{
    private WordleGame wordleGame;
    private Map<Button, KeyboardKey> keyboardKeyButtonMap = new HashMap<>();
    private StringBuilder currentAttempt = new StringBuilder();

    private TextView attemptCountDebugLabel, attemptBufferDebugLabel;

    private Map<Integer, WordleTextField> attemptTextFields;
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
        //Keyboard Row 1
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_Q), KeyboardKey.Q);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_W), KeyboardKey.W);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_E), KeyboardKey.E);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_R), KeyboardKey.R);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_T), KeyboardKey.T);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_Y), KeyboardKey.Y);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_U), KeyboardKey.U);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_I), KeyboardKey.I);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_O), KeyboardKey.O);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_P), KeyboardKey.P);

        //Keyboard Row 2
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_A), KeyboardKey.A);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_S), KeyboardKey.S);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_D), KeyboardKey.D);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_F), KeyboardKey.F);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_G), KeyboardKey.G);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_H), KeyboardKey.H);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_J), KeyboardKey.J);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_K), KeyboardKey.K);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_L), KeyboardKey.L);

        //Keyboard Row 3
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_ENTER), KeyboardKey.ENTER);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_Z), KeyboardKey.Z);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_X), KeyboardKey.X);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_C), KeyboardKey.C);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_V), KeyboardKey.V);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_B), KeyboardKey.B);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_N), KeyboardKey.N);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_M), KeyboardKey.M);
        keyboardKeyButtonMap.put(findViewById(R.id.keyboard_BACKSPACE), KeyboardKey.BACKSPACE);

        Set<Button> keyboardButtons = keyboardKeyButtonMap.keySet();
        for(Button button : keyboardButtons)
        {
            KeyboardKey key = keyboardKeyButtonMap.get(button);
            if(key == null) continue;

            switch(key)
            {
                case ENTER:
                    button.setOnClickListener((View view) ->
                    {
                        submitAttempt();
                    });
                    break;

                case BACKSPACE:
                    button.setOnClickListener((View view) ->
                    {
                        removeCharFromAttempt();
                    });
                    break;

                default:
                    button.setOnClickListener((View view) ->
                    {
                        addCharToAttempt(key.getChar());
                    });
                    break;
            }
        }
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
        attemptTextFields = new HashMap<>();
        attemptTextFields.put(1, WordleTextField.create(WordleTextField.Row.ROW_1, this));
        attemptTextFields.put(2, WordleTextField.create(WordleTextField.Row.ROW_2, this));
        attemptTextFields.put(3, WordleTextField.create(WordleTextField.Row.ROW_3, this));
        attemptTextFields.put(4, WordleTextField.create(WordleTextField.Row.ROW_4, this));
        attemptTextFields.put(5, WordleTextField.create(WordleTextField.Row.ROW_5, this));
        attemptTextFields.put(6, WordleTextField.create(WordleTextField.Row.ROW_6, this));
    }

    private void submitAttempt()
    {
        ArrayList<WordleGame.CharacterStatus> charStatuses = wordleGame.submitGuess(currentAttempt.toString());
        if(charStatuses == null) return;

        for(int i = 0; i < currentAttempt.length(); i++)
        {
            Button keyButton = getKeyboardButton(KeyboardKey.get(currentAttempt.charAt(i)));
            //if(keyButton == null) continue;

            switch(charStatuses.get(i))
            {
                case PRESENT_AND_CORRECT_POSITION:
                    keyButton.setBackgroundColor(getColor(R.color.wordle_green));

                    attemptTextFields.get(wordleGame.getAttemptsCompleted())
                            .getColumn(i)
                            .setBackgroundColor(getColor(R.color.wordle_green));

                    attemptTextFields.get(wordleGame.getAttemptsCompleted())
                            .getColumn(i)
                            .setTextColor(getColor(R.color.white));
                    break;

                case PRESENT_BUT_INCORRECT_POSITION:
                    keyButton.setBackgroundColor(getColor(R.color.wordle_yellow));

                    attemptTextFields.get(wordleGame.getAttemptsCompleted())
                            .getColumn(i)
                            .setBackgroundColor(getColor(R.color.wordle_yellow));

                    attemptTextFields.get(wordleGame.getAttemptsCompleted())
                            .getColumn(i)
                            .setTextColor(getColor(R.color.white));
                    break;

                case NOT_PRESENT:
                    keyButton.setBackgroundColor(getColor(R.color.wordle_gray));

                    attemptTextFields.get(wordleGame.getAttemptsCompleted())
                            .getColumn(i)
                            .setBackgroundColor(getColor(R.color.wordle_gray));

                    attemptTextFields.get(wordleGame.getAttemptsCompleted())
                            .getColumn(i)
                            .setTextColor(getColor(R.color.white));
                    break;
            }
        }

        //Reset currentAttempt buffer
        currentAttempt.setLength(0);

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
        if(currentAttempt.length() == wordleGame.getWordToGuess().length())
            return;
        currentAttempt.append(c);

        //Debug Info
        if(doDebugLogging)
        {
            attemptBufferDebugLabel.setText(String.format("AttemptBuffer: \"%s\"", currentAttempt));
        }
    }

    private void removeCharFromAttempt()
    {
        if(!currentAttempt.isEmpty())
            currentAttempt.setLength(currentAttempt.length() - 1);

        //Debug Info
        if(doDebugLogging)
        {
            attemptBufferDebugLabel.setText(String.format("AttemptBuffer: \"%s\"", currentAttempt));
        }
    }

    private Button getKeyboardButton(KeyboardKey key)
    {
        for(Button button : keyboardKeyButtonMap.keySet())
        {
            if(keyboardKeyButtonMap.get(button) == key)
                return button;
        }
        return null;
    }

    private enum KeyboardKey
    {
        Q('Q'), W('W'), E('E'), R('R'), T('T'), Y('Y'), U('U'), I('I'), O('O'), P('P'),
        A('A'), S('S'), D('D'), F('F'), G('G'), H('H'), J('J'), K('K'), L('L'),
        Z('Z'), X('X'), C('C'), V('V'), B('B'), N('N'), M('M'),
        ENTER('\r'),
        BACKSPACE('\b');

        private char asciiChar;
        private
        KeyboardKey(char c)
        {
            this.asciiChar = c;
        }

        public char getChar()
        {
            return this.asciiChar;
        }

        private static KeyboardKey get(char c)
        {
            for(KeyboardKey key : KeyboardKey.values())
            {
                if(key.getChar() == c) return key;
            }
            return null;
        }
    }
}