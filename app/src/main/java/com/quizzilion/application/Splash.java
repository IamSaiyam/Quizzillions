package com.quizzilion.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    TextView logo;

    private String PARAM_ONE = "Email";
    private String PARAM_TWO = "Password";
    private String PARAM_THREE = "userLoggedInState";
    public static final String FILE_NAME = "UserInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        wait5Seco();

    }

    public void wait5Seco(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                if(!isNetworkAvailable()){
                    Toast.makeText(Splash.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // yourMethod();
                            finishAffinity();
                        }
                    }, 2000);   //5 seconds
                }else{
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
                    if(pref.getBoolean(PARAM_THREE, false))
                    {
                        Intent i = new Intent(Splash.this, AdminDashboard.class);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(Splash.this, Landing.class);
                        startActivity(i);
                    }
                }

            }
        }, 3000);   //5 seconds
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}