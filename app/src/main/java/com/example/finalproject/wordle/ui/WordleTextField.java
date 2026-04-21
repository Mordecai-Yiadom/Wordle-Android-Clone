package com.example.finalproject.wordle.ui;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.util.ArrayList;

public class WordleTextField
{
    private ArrayList<TextView> textViews;
    private WordleTextField()
    {
        textViews = new ArrayList<>();
    }

    public static WordleTextField create(Row row, AppCompatActivity activity)
    {
        if(activity == null || row == null) return null;

        WordleTextField textField = new WordleTextField();
        for(int id : row.columnIds)
        {
            textField.textViews.add(activity.findViewById(id));
        }
        return textField;
    }

    public TextView getColumn(int index)
    {
        return textViews.get(index);
    }

    public enum Row
    {
        ROW_1(R.id.attemptContainer_row1_char1, R.id.attemptContainer_row1_char2,
                R.id.attemptContainer_row1_char3, R.id.attemptContainer_row1_char4,
                R.id.attemptContainer_row1_char5),
        ROW_2(R.id.attemptContainer_row2_char1, R.id.attemptContainer_row2_char2,
                R.id.attemptContainer_row2_char3, R.id.attemptContainer_row2_char4,
                R.id.attemptContainer_row2_char5),
        ROW_3(R.id.attemptContainer_row3_char1, R.id.attemptContainer_row3_char2,
                R.id.attemptContainer_row3_char3, R.id.attemptContainer_row3_char4,
                R.id.attemptContainer_row3_char5),
        ROW_4(R.id.attemptContainer_row4_char1, R.id.attemptContainer_row4_char2,
                R.id.attemptContainer_row4_char3, R.id.attemptContainer_row4_char4,
                R.id.attemptContainer_row4_char5),
        ROW_5(R.id.attemptContainer_row5_char1, R.id.attemptContainer_row5_char2,
                R.id.attemptContainer_row5_char3, R.id.attemptContainer_row5_char4,
                R.id.attemptContainer_row5_char5),
        ROW_6(R.id.attemptContainer_row6_char1, R.id.attemptContainer_row6_char2,
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
