package com.quizzilion.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Locale;

public class AdminLogin extends AppCompatActivity {

    TextView signup, login;
    EditText username, pass;

    private String PARAM_ONE = "Email";
    private String PARAM_TWO = "Password";
    private String PARAM_THREE = "userLoggedInState";
    public static final String FILE_NAME = "UserInfo";


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        if(pref.getBoolean(PARAM_THREE, false))
        {
            goToHome();
        }

        signup = findViewById(R.id.go_to_register);
        login = findViewById(R.id.login_btn);
        username = findViewById(R.id.login_email);
        pass = findViewById(R.id.login_pass);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminLogin.this, AdminRegister.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username.getText().toString().trim().toLowerCase();
                String password = pass.getText().toString().trim();
                if(email.isEmpty())
                    Toast.makeText(AdminLogin.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                else if(password.isEmpty())
                    Toast.makeText(AdminLogin.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                else{
                    db.collection("Admins").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    if (password.equals(document.getString("Password"))) {
                                        Toast.makeText(AdminLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString(PARAM_ONE, email);
                                        editor.putString(PARAM_TWO, password);
                                        editor.putBoolean(PARAM_THREE, true);
                                        editor.commit();
                                        goToHome();
                                    } else {
                                        Toast.makeText(AdminLogin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(AdminLogin.this, "No User found.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminLogin.this, "Something went worng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminLogin.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    public void goToHome(){
        Intent i = new Intent(AdminLogin.this, AdminDashboard.class);
        startActivity(i);
    }
}