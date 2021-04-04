package com.example.flashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.animation.*;

import android.widget.Button;
import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Flashcard> allFlashcards;
    FlashcardDatabase flashcardDatabase;
    int currentCardDisplayedIndex = 0;

    TextView flashcard_question;
    TextView flashcard_answer;

    String question;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcard_question = (TextView) findViewById(R.id.flashcard_question);
        flashcard_answer = (TextView) findViewById(R.id.flashcard_answer);

        question = "Who is the 44th president of the United States?";
        answer = "Barack Obama";

        flashcardDatabase = new FlashcardDatabase((getApplicationContext()));
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            question = allFlashcards.get(0).getQuestion();
            answer = allFlashcards.get(0).getAnswer();

            flashcard_question.setText(question);
            flashcard_answer.setText(answer);
        }

        flashcard_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get center of the circle
                int cx = flashcard_answer.getWidth() / 2;
                int cy = flashcard_answer.getHeight() / 2;

                // get the final radius for the clipping cirve
                float final_radius = (float) Math.hypot(cx, cy);
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcard_answer, cx, cy, 0f, final_radius);

                flashcard_question.setVisibility(View.INVISIBLE);
                flashcard_answer.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

        flashcard_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_question.setVisibility(View.VISIBLE);
                flashcard_answer.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 0);
                // first parameter is the "enter" animation for the new launched activity
                // second parameter is the "exit" animation for the current activity we're leaving
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        findViewById(R.id.flashcard_question).startAnimation(rightInAnim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                if (flashcard_question.getVisibility() == View.INVISIBLE){
                    flashcard_question.setVisibility(View.VISIBLE);
                    flashcard_answer.setVisibility(View.INVISIBLE);
                }
                if (allFlashcards.size() == 0)
                    return;

                // advance pointer index
                currentCardDisplayedIndex = (currentCardDisplayedIndex + 1) % allFlashcards.size();
                // set the question and answer textViews
                question = allFlashcards.get(currentCardDisplayedIndex).getQuestion();
                answer = allFlashcards.get(currentCardDisplayedIndex).getAnswer();

                flashcard_question.setText(question);
                flashcard_answer.setText(answer);
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

        flashcard_question.setText(question);
        flashcard_answer.setText(answer);

        flashcardDatabase.insertCard(new Flashcard(question, answer));
        allFlashcards = flashcardDatabase.getAllCards();
  }
}