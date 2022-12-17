package com.quizzilion.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quizzilion.application.Adapters.QuizzesAdapter;

import java.util.ArrayList;

public class AdminResults extends AppCompatActivity {

    RecyclerView quizzesRV;
    QuizzesAdapter quizzesAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ArrayList<Quiz> quizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_results);

        quizzesRV = findViewById(R.id.quizzesRV);

        quizes = new ArrayList<>();

        CollectionReference ref = db.collection("Quizzes");

        quizzesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        quizzesAdapter = new QuizzesAdapter(quizes,this);
        quizzesRV.setAdapter(quizzesAdapter);

        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Quiz q = document.toObject(Quiz.class);
                        quizes.add(q);
                    }
                    quizzesAdapter.notifyDataSetChanged();

//                    Toast.makeText(AdminResults.this, quizes.get(0).getParticipants().size()+"", Toast.LENGTH_SHORT).show();


                }
            }
        });




    }
}