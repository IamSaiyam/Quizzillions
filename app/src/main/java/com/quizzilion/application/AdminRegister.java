package com.quizzilion.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdminRegister extends AppCompatActivity {

    TextView login, register;
    EditText name, email, pass, confirmPass;
    ProgressBar pb;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        login = findViewById(R.id.go_to_login);
        name = findViewById(R.id.name_input);
        email = findViewById(R.id.email_input);
        pass = findViewById(R.id.pass_input);
        confirmPass = findViewById(R.id.confirm_pass);
        register = findViewById(R.id.register_btn);
        pb = findViewById(R.id.progress_bar);

        pb.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString().trim();
                String userEmail = email.getText().toString().trim().toLowerCase();
                String password = pass.getText().toString().trim();
                String confirm = confirmPass.getText().toString().trim();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(AdminRegister.this, "Name can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                    Toast.makeText(AdminRegister.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirm)) {
                    Toast.makeText(AdminRegister.this, "Password din't match!", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<5){
                    Toast.makeText(AdminRegister.this, "Password length should be more than 5", Toast.LENGTH_SHORT).show();
                }
                else {
                    pb.setVisibility(View.VISIBLE);
                    Map<String, String> user = new HashMap<>();
                    user.put("Name", userName);
                    user.put("Email", userEmail);
                    user.put("Password", confirm);

                    db.collection("Admins").document(userEmail).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Toast.makeText(AdminRegister.this, "User with this e-mail already exists.", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(View.GONE);
                                } else {
                                    db.collection("Admins").document(userEmail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AdminRegister.this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show();
                                            goToLogin();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AdminRegister.this, "Can't Register user", Toast.LENGTH_SHORT).show();
                                            pb.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(AdminRegister.this, "Please try again after some time", Toast.LENGTH_SHORT).show();
                                pb.setVisibility(View.GONE);
                            }
                        }
                    });


                }
            }
        });


    }

    public void goToLogin(){
        Intent i = new Intent(AdminRegister.this, AdminLogin.class);
        startActivity(i);
    }
}