package com.quizzilion.application;

import static com.quizzilion.application.Landing.quizToGive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TheQuiz extends AppCompatActivity {

    TextView question, submitNext, prevQuestion, timer, questionNum;
    RadioButton option1RB, option2RB, option3RB, option4RB;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String email, name;

    int i;
    public static Integer score;
    public static Float percent;
    HashMap<Integer, Integer> scores = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_quiz);

        question = findViewById(R.id.question_quiz);
        option1RB = findViewById(R.id.op1_quiz);
        option2RB = findViewById(R.id.op2_quiz);
        option3RB = findViewById(R.id.op3_quiz);
        option4RB = findViewById(R.id.op4_quiz);
        submitNext = findViewById(R.id.next_question_btn);
        prevQuestion = findViewById(R.id.prev_quesion_btn);
        timer = findViewById(R.id.timerTv);
        questionNum = findViewById(R.id.question_num_quiz);

        score = 0;
        percent = 0.0f;

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        setQuestion(0);
        optionsConfigure(option1RB, option2RB, option3RB, option4RB);

        CountDownTimer countDown;
        countDown= new CountDownTimer(quizToGive.getTimeLimit()*60*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                timer.setText(minutes + ":" + seconds);
            }

            public void onFinish() {
                Toast.makeText(TheQuiz.this, "Times Up!", Toast.LENGTH_SHORT).show();
                percent = (float)score/quizToGive.getNoOfQuestions() * 100;
                updateScore();
                Intent i = new Intent(TheQuiz.this, QuizEnd.class);
                startActivity(i);
            }
        };
        countDown.start();

        submitNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenOption = "";
                if(option2RB.isChecked()) chosenOption = option2RB.getText().toString();
                else if(option3RB.isChecked()) chosenOption = option3RB.getText().toString();
                else if(option4RB.isChecked()) chosenOption = option4RB.getText().toString();
                else chosenOption = option1RB.getText().toString();
                nextQuestion(chosenOption);
            }
        });

        prevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevQuestion();
            }
        });

    }


    public void setQuestion(int index){
        questionNum.setText("Q" + (index+1));
        Question q = quizToGive.getQuestionSet().get(index);
        question.setText(q.getQuestion());
        option1RB.setText(q.getOptions().get(0));
        option2RB.setText(q.getOptions().get(1));
        option3RB.setText(q.getOptions().get(2));
        option4RB.setText(q.getOptions().get(3));
    }

    public void nextQuestion(String chosen){
        if(chosen.equals(quizToGive.getQuestionSet().get(i).getCorrectAnswer())) {
            if(!scores.containsKey(i)) {
                score++;
                scores.put(i, 1);
            }
        }else{
            if(scores.containsKey(i)){
                score--;
                scores.remove(i);
            }
        }
        if(i+1<quizToGive.getNoOfQuestions()) {
            i++;
            setQuestion(i);
            resetOptions();
        }else {
            showAlert();
        }
    }

    public void prevQuestion(){
        if(i-1>=0) {
            i--;
            setQuestion(i);
            resetOptions();
        }
    }

    public void resetOptions(){
        option1RB.setChecked(false);
        option2RB.setChecked(false);
        option3RB.setChecked(false);
        option4RB.setChecked(false);
    }

    public void stopQuiz(){
        option1RB.setEnabled(false);
        option2RB.setEnabled(false);
        option3RB.setEnabled(false);
        option4RB.setEnabled(false);
        timer.setVisibility(View.GONE);
        submitNext.setText("View Results");
        question.setText("The quiz is now ended, you can view you results.");
        prevQuestion.setVisibility(View.GONE);
        submitNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percent = (float)score/quizToGive.getNoOfQuestions() * 100;
                updateScore();
                Intent i = new Intent(TheQuiz.this, QuizEnd.class);
                startActivity(i);
            }
        });
    }


    public void showAlert(){
        new SweetAlertDialog(TheQuiz.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure you want to submit the test?")
                .setContentText("Cannot attempt questions, once submitted")
                .setConfirmText("Yes, Submit")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        stopQuiz();
                        percent = (float)score/quizToGive.getNoOfQuestions() * 100;
                        updateScore();
                        sDialog
                                .setTitleText("Submitted")
                                .setContentText("Your test is submitted")
                                .setConfirmText("View Results")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(TheQuiz.this, QuizEnd.class);
                                        startActivity(i);
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    public void updateScore(){
        Participant participant = new Participant(name, email, percent, score, 0);
        db.collection("Quizzes").document(quizToGive.getId()).collection("Participants").document(email).set(participant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(TheQuiz.this, "Score Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TheQuiz.this, "Error occured while updating score", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void optionsConfigure(RadioButton op1RB, RadioButton op2RB, RadioButton op3RB, RadioButton op4RB) {
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
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        showAlert();
    }
}