package com.quizzilion.application;

import static com.quizzilion.application.AdminDashboard.adminEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class CreateQuiz extends AppCompatActivity {

    TextView next;
    EditText quizName, questionNo;
    RadioButton privateQuiz, publicQuiz;
    Spinner totalTime;
    public static Quiz quiz;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        next = findViewById(R.id.next_in_create);
        quizName = findViewById(R.id.quiz_name);
        publicQuiz = findViewById(R.id.public_quiz);
        privateQuiz = findViewById(R.id.private_quiz);
        questionNo = findViewById(R.id.questions_nos);
        totalTime = findViewById(R.id.test_time_spinner);

        publicQuiz.setChecked(true);

        privateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicQuiz.setSelected(true);
                privateQuiz.setSelected(false);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.test_time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totalTime.setAdapter(adapter);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = quizName.getText().toString().trim();
                id += (name.toUpperCase().split(" ")[0] + new Random().nextInt(9999) + 1000).substring(0, name.toUpperCase().split(" ")[0].length() + 4);
                Boolean access = false;
                if(publicQuiz.isSelected()) access = true;
                else access = false;
                String questionNumber = questionNo.getText().toString().trim();
                String time = totalTime.getSelectedItem().toString().trim();
                int timeInInt = Integer.parseInt(time.substring(0,2));
                if(name.isEmpty()) Toast.makeText(CreateQuiz.this, "Name can't be blank", Toast.LENGTH_SHORT).show();
//                else if(questionNumber.length()<1 || Integer.parseInt(questionNumber)<5) Toast.makeText(CreateQuiz.this, "Questions should be more than 5", Toast.LENGTH_SHORT).show();
//                else if(Integer.parseInt(questionNumber)>timeInInt*2)
//                    Toast.makeText(CreateQuiz.this, "Too less questions for given time", Toast.LENGTH_SHORT).show();
                else {
                    quiz = new Quiz(name, access, Integer.parseInt(questionNumber), timeInInt, id, adminEmail, new ArrayList<Question>(), new ArrayList<Participant>());
                    Intent i = new Intent(CreateQuiz.this, SetQuestions.class);
                    startActivity(i);
                }
            }
        });



    }
}