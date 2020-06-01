package com.example.womensaviours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!=null){
                    startActivity(new Intent(SplashActivity.this,
                            MainActivity.class));
                    finish();}
                else{
                    startActivity(new Intent(SplashActivity.this,
                            SlidesActivity.class));
                    finish();
                }
            }
        },3000);
    }
}
