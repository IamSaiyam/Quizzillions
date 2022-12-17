package com.quizzilion.application;

import static com.quizzilion.application.AdminLogin.FILE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdminDashboard extends AppCompatActivity {

    TextView logout, createQuiz, quizHistory, editQuiz, quizResults;
    public static String adminEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        logout = findViewById(R.id.logout_btn);
        createQuiz = findViewById(R.id.create_quiz);
        quizHistory = findViewById(R.id.quiz_history);
        editQuiz = findViewById(R.id.edit_quiz);
        quizResults = findViewById(R.id.quiz_results);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        adminEmail = pref.getString("Email", "default@default.com");;

        
        createQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminDashboard.this, CreateQuiz.class);
                startActivity(i);
            }
        });

        quizResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminDashboard.this, AdminResults.class);
                startActivity(i);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.clear().commit();
                Intent i = new Intent(AdminDashboard.this, Landing.class);
                startActivity(i);
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            this.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}