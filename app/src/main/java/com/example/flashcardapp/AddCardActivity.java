package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.exitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuestion = ((EditText) findViewById(R.id.questionTextField)).getText().toString();
                String newAnswer = ((EditText) findViewById(R.id.answerTextField)).getText().toString();

                Intent data = new Intent();
                data.putExtra("QUESTION", newQuestion);
                data.putExtra("ANSWER", newAnswer);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}