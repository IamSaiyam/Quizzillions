package com.quizzilion.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class Landing extends AppCompatActivity {

    TextView enterQuiz, createQuiz;
    EditText enteredQuizId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Quiz quizToGive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        enterQuiz = findViewById(R.id.enter_quiz_btn);
        createQuiz = findViewById(R.id.create_quiz_btn);
        enteredQuizId = findViewById(R.id.quiz_id_input);

        createQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Landing.this, AdminLogin.class);
                startActivity(i);
            }
        });

        enterQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quizId = enteredQuizId.getText().toString().trim();
                if(quizId.isEmpty())
                    Toast.makeText(Landing.this, "Enter a quiz id", Toast.LENGTH_SHORT).show();
                else{
                    db.collection("Quizzes").document(quizId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    quizToGive = document.toObject(Quiz.class);
                                    Intent i = new Intent(Landing.this, EnterQuiz.class);
                                    startActivity(i);
                                }else {
                                    Toast.makeText(Landing.this, "Invalid Quiz ID", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Landing.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Landing.this, "Something went wrong! Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }


}