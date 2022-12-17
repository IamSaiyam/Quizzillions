package com.quizzilion.application;

import static com.quizzilion.application.CreateQuiz.quiz;
import static com.quizzilion.application.Landing.quizToGive;
import static com.quizzilion.application.TheQuiz.percent;
import static com.quizzilion.application.TheQuiz.score;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class QuizEnd extends AppCompatActivity {

    TextView name, id, correct, questions, scoreTV, share;
    ConstraintLayout root;
    ImageView copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);

        name = findViewById(R.id.name_share);
        id = findViewById(R.id.id_share);
        correct = findViewById(R.id.quiz_correct);
        questions = findViewById(R.id.questions_share);
        scoreTV = findViewById(R.id.quiz_score);
        copy = findViewById(R.id.copy);
        share = findViewById(R.id.shareBtn);
        root = findViewById(R.id.root);

        name.setText(quizToGive.getName());
        id.setText(quizToGive.getId());
        questions.setText(quizToGive.getNoOfQuestions() + "");
        scoreTV.setText(percent + "%");
        correct.setText(score + "");

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipBoard();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "Hurray! I have completed the quiz: " + id.getText() + "and scored " + percent + "%." + "\nQuizzillions is the best platform to take and conduct quizzes do join us.";
                shareText(s);
            }
        });

    }



    public void shareText(String sharebody){
        Intent intentt = new Intent(Intent.ACTION_SEND);

        intentt.setType("text/plain");
        intentt.putExtra(Intent.EXTRA_SUBJECT, "Quizzilions");

        intentt.putExtra(Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(intentt, "Share Via"));
    }

    public void copyToClipBoard(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Results", "Hurray! I have completed the quiz: " + id.getText() + "\nand scored " + percent + "%.");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Results copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(QuizEnd.this, Landing.class);
        startActivity(i);
    }
}