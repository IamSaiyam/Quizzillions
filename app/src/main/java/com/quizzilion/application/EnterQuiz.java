package com.quizzilion.application;


import static com.quizzilion.application.Landing.quizToGive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;

public class EnterQuiz extends AppCompatActivity {

    TextView name, id, limit, questions, access, startQuiz;
    EditText userEmailET, userNameET;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_quiz);


        name = findViewById(R.id.name_share);
        id = findViewById(R.id.id_share);
        limit = findViewById(R.id.time_limit_share);
        questions = findViewById(R.id.questions_share);
        access = findViewById(R.id.quiz_type_share);
        userNameET = findViewById(R.id.user_name_enter);
        userEmailET = findViewById(R.id.user_email_enter);
        startQuiz = findViewById(R.id.start_quiz_btn);

        name.setText(quizToGive.getName());
        id.setText(quizToGive.getId());
        limit.setText(quizToGive.getTimeLimit() + "");
        questions.setText(quizToGive.getNoOfQuestions() + "");
        if(quizToGive.getPrivate()) access.setText("Private");
        else access.setText("Public");

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNameET.getText().toString().trim().isEmpty())
                    Toast.makeText(EnterQuiz.this, "Name can't be empty", Toast.LENGTH_SHORT).show();
                else if(userEmailET.getText().toString().trim().isEmpty())
                    Toast.makeText(EnterQuiz.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(userEmailET.getText().toString().trim().toLowerCase()).matches())
                    Toast.makeText(EnterQuiz.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                else{
                    DocumentReference ref = db.collection("Quizzes").document(id.getText().toString()).collection("Participants").document(userEmailET.getText().toString().trim().toLowerCase());
                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    Toast.makeText(EnterQuiz.this, "You have already given the test", Toast.LENGTH_SHORT).show();
                                }else{
                                    if(quizToGive.getCreator().equals(userEmailET.getText().toString().toLowerCase(Locale.ROOT))){
                                        Toast.makeText(EnterQuiz.this, "Creator can't give the test", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Participant participant = new Participant(userNameET.getText().toString().trim(), userEmailET.getText().toString().trim().toLowerCase(), 0.0f, 0, 0);
                                        ref.set(participant);
                                        Intent i = new Intent(EnterQuiz.this, TheQuiz.class);
                                        i.putExtra("email", userEmailET.getText().toString().trim().toLowerCase());
                                        i.putExtra("name", userNameET.getText().toString().trim());
                                        startActivity(i);
                                    }
                                }
                            }else{
                                Toast.makeText(EnterQuiz.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EnterQuiz.this, "Something went wrong, Please check your network connection", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });


    }
}