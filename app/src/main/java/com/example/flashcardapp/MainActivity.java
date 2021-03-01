package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button optionA = (Button) findViewById(R.id.optionA);
        Button optionB = (Button) findViewById(R.id.optionB);
        Button optionC = (Button) findViewById((R.id.optionC));
        Button showAnswers = (Button) findViewById(R.id.showAnswers);
        TextView questionBox = (TextView) findViewById(R.id.questionBox);


        final boolean[] visible = {true, false};
        final boolean[] clicked = {false};

        showAnswers.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                 clicked[0] = false;
                 optionA.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
                 optionB.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
                 optionC.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
             }
        });

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked[0]){
                    optionA.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                    clicked[0] = true;
                }

            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked[0]){
                    optionA.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                    optionB.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    clicked[0] = true;
                }

            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked[0]){
                    optionA.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                    optionC.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    clicked[0] = true;
                }
            }
        });

        questionBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visible[0]) {
                    optionA.setVisibility(View.INVISIBLE);
                    optionB.setVisibility(View.INVISIBLE);
                    optionC.setVisibility(View.INVISIBLE);
                } else {
                    optionA.setVisibility(View.VISIBLE);
                    optionB.setVisibility(View.VISIBLE);
                    optionC.setVisibility(View.VISIBLE);
                }

                visible[0] = !visible[0];
            }
        });
    }
}