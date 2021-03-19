package com.example.flashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Flashcard> allFlashcards;
    FlashcardDatabase flashcardDatabase;
    int currentCardDisplayedIndex = 0;

    TextView questionBox;
    String question;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button optionA = (Button) findViewById(R.id.optionA);
        Button optionB = (Button) findViewById(R.id.optionB);
        Button optionC = (Button) findViewById((R.id.optionC));
        Button resetColor = (Button) findViewById(R.id.showAnswers);
        Button hideAnswers = (Button) findViewById(R.id.hideAnswers);

        questionBox = (TextView) findViewById(R.id.questionBox);

        final boolean[] showAnswer = {true};
        final boolean[] visible = {true, false};
        final boolean[] clicked = {false};

        flashcardDatabase = new FlashcardDatabase((getApplicationContext()));
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            question = allFlashcards.get(0).getQuestion();
            answer = allFlashcards.get(0).getAnswer();

            questionBox.setText(question);
        }
        else {
            question = "Who is the 44th president of the United States?";
            answer = "Barack Obama";
        }


        resetColor.setOnClickListener(new View.OnClickListener() {
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
                if (!showAnswer[0]){
                    questionBox.setText(answer);
                    System.out.println(answer);
                    questionBox.setBackgroundColor(getResources().getColor(R.color.green));
                    showAnswer[0] = true;
                }
                else {
                    questionBox.setText(question);
                    System.out.println(question);
                    questionBox.setBackgroundResource(R.drawable.question_box_styling);
                    showAnswer[0] = false;
                }

            }
        });

        hideAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible[0]) {
                    optionA.setVisibility(View.INVISIBLE);
                    optionB.setVisibility(View.INVISIBLE);
                    optionC.setVisibility(View.INVISIBLE);
                    visible[0] = false;
                }
                else {
                    optionA.setVisibility(View.VISIBLE);
                    optionB.setVisibility(View.VISIBLE);
                    optionC.setVisibility(View.VISIBLE);
                    visible[0] = true;
                }

            }
        });

        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() == 0)
                    return;

                // advance pointer index
                currentCardDisplayedIndex = (currentCardDisplayedIndex + 1) % allFlashcards.size();
                // set the question and answer textViews
                question = allFlashcards.get(currentCardDisplayedIndex).getQuestion();
                answer = allFlashcards.get(currentCardDisplayedIndex).getAnswer();

                questionBox.setText(question);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            question = data.getStringExtra("QUESTION");
            answer = data.getStringExtra("ANSWER");
        }

        questionBox.setText(question);
        flashcardDatabase.insertCard(new Flashcard(question, answer));
        allFlashcards = flashcardDatabase.getAllCards();
  }
}