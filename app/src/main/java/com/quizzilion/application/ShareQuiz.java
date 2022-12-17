package com.quizzilion.application;

import static com.quizzilion.application.CreateQuiz.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShareQuiz extends AppCompatActivity {

    TextView name, id, limit, questions, access, share;
    ImageView copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_quiz);

        name = findViewById(R.id.name_share);
        id = findViewById(R.id.id_share);
        limit = findViewById(R.id.time_limit_share);
        questions = findViewById(R.id.questions_share);
        access = findViewById(R.id.quiz_type_share);
        copy = findViewById(R.id.copy);
        share = findViewById(R.id.shareBtn);

        name.setText(quiz.getName());
        id.setText(quiz.getId());
        limit.setText(quiz.getTimeLimit() + "");
        questions.setText(quiz.getNoOfQuestions() + "");
        if(quiz.getPrivate()) access.setText("Private");
        else access.setText("Public");

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sharebody = "Join my quiz on quizzillions. Its the best platform to give and create quizzes.\nMy Quiz ID: " + id.getText();
                shareText(sharebody);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipBoard();
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
        ClipData clip = ClipData.newPlainText("id", id.getText() + "");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Quiz ID copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ShareQuiz.this, AdminDashboard.class);
        startActivity(i);
    }
}