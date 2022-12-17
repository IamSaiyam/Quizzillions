package com.quizzilion.application;

import static com.quizzilion.application.CreateQuiz.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SetQuestions extends AppCompatActivity {

    ArrayList<Question> listOfQuestions = new ArrayList<>();
    TextView addQuestion, prevQuestion, questionNum;
    EditText questionET, option1ET, option2ET, option3ET, option4ET;
    RadioButton op1RB, op2RB, op3RB, op4RB;



    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int counter = 1;
    int limit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_questions);

        questionET = findViewById(R.id.questionET);
        option1ET = findViewById(R.id.op1ET);
        option2ET = findViewById(R.id.op2ET);
        option3ET = findViewById(R.id.op3ET);
        option4ET = findViewById(R.id.op4ET);
        op1RB = findViewById(R.id.op1RB);
        op2RB = findViewById(R.id.op2RB);
        op3RB = findViewById(R.id.op3RB);
        op4RB = findViewById(R.id.op4RB);
        addQuestion = findViewById(R.id.addQuesion);
        prevQuestion = findViewById(R.id.prevQuesion);
        questionNum = findViewById(R.id.question_num);

        counter = 1;
        limit = quiz.getNoOfQuestions();

        op1RB.setChecked(true);


        op1RB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op1RB.setChecked(true);
                op2RB.setChecked(false);
                op3RB.setChecked(false);
                op4RB.setChecked(false);
            }
        });

        op2RB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op1RB.setChecked(false);
                op2RB.setChecked(true);
                op3RB.setChecked(false);
                op4RB.setChecked(false);
            }
        });

        op3RB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op1RB.setChecked(false);
                op2RB.setChecked(false);
                op3RB.setChecked(true);
                op4RB.setChecked(false);
            }
        });

        op4RB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op1RB.setChecked(false);
                op2RB.setChecked(false);
                op3RB.setChecked(false);
                op4RB.setChecked(true);
            }
        });


        prevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String question = questionET.getText().toString().trim();
                    ArrayList<String> options = new ArrayList<>();
                    String answer = "";
                    String option1 = option1ET.getText().toString().trim();
                    String option2 = option2ET.getText().toString().trim();
                    String option3 = option3ET.getText().toString().trim();
                    String option4 = option4ET.getText().toString().trim();

                    if (question.length() < 2)
                        Toast.makeText(SetQuestions.this, "Question length too short", Toast.LENGTH_SHORT).show();
                    else if (option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty())
                        Toast.makeText(SetQuestions.this, "No option can be empty", Toast.LENGTH_SHORT).show();
                    else {
                        if (op2RB.isChecked()) answer = option2;
                        else if (op3RB.isChecked()) answer = option3;
                        else if (op4RB.isChecked()) answer = option4;
                        else answer = option1;
                        options.add(option1);
                        options.add(option2);
                        options.add(option3);
                        options.add(option4);


                        listOfQuestions.add(new Question(question, answer, options));

                        counter++;
                        if(counter>limit){
                            quiz.setQuestionSet(listOfQuestions);
                            uploadQuiz();
                            Intent i = new Intent(SetQuestions.this, ShareQuiz.class);
                            startActivity(i);
                        }
                        reset();
                        Toast.makeText(SetQuestions.this, "Question added", Toast.LENGTH_SHORT).show();
                    }



            }
        });

    }

    public void reset(){
        if(counter==limit){
            addQuestion.setText("Finish");
        }
        questionET.setText("");
        option1ET.setText("");
        option2ET.setText("");
        option3ET.setText("");
        option4ET.setText("");
        questionNum.setText("Q" + counter);
    }

    public void uploadQuiz(){
        DocumentReference dbRef = db.collection("Quizzes").document(quiz.getId());
        dbRef.set(quiz).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SetQuestions.this, "Quiz Published", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SetQuestions.this, "Failed: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}