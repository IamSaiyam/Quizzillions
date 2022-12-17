package com.quizzilion.application;

import static com.quizzilion.application.AdminResults.quizes;
import static com.quizzilion.application.CreateQuiz.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quizzilion.application.Adapters.ParticipantAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizDetails extends AppCompatActivity {

    TextView name, id, limit, questions, access;
    RecyclerView participantsRv;
    ParticipantAdapter pAdapter;

    ArrayList<Participant> participantsOfQuiz;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_details);

        name = findViewById(R.id.name_share);
        id = findViewById(R.id.quiz_id_share);
        limit = findViewById(R.id.time_limit_share);
        questions = findViewById(R.id.questions_share);
        access = findViewById(R.id.quiz_type_share);

        participantsRv = findViewById(R.id.participantsRV);

        int index = Integer.parseInt(getIntent().getStringExtra("Index"));
        Integer numOfQuestions = quizes.get(index).getNoOfQuestions();

        name.setText(quizes.get(index).getName());
        id.setText(quizes.get(index).getId());
        limit.setText(quizes.get(index).getTimeLimit() + "");
        questions.setText(quizes.get(index).getNoOfQuestions() + "");
        if(quizes.get(index).getPrivate()) access.setText("Private");
        else access.setText("Public");

        participantsOfQuiz = new ArrayList<>();



        participantsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        pAdapter = new ParticipantAdapter(participantsOfQuiz, this, numOfQuestions);
        participantsRv.setAdapter(pAdapter);
        db.collection("Quizzes").document(quizes.get(index).getId()).collection("Participants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult())
                        participantsOfQuiz.add(doc.toObject(Participant.class));

                    pAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(QuizDetails.this, "Can't fetch the participants.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}