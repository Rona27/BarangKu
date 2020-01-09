package com.example.barangqu.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.barangqu.R;

public class Splash extends AppCompatActivity {

    private int waktu_loading=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setalah loading ke login
                Intent login= new Intent(Splash.this, LoginActivity.class);
                startActivity(login);
                finish();
            }

        }, waktu_loading);



    }

}
