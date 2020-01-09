package com.example.barangqu.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barangqu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class MypostActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView tvJudulpost, tvDeskrip;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView imgBarangPost;
    private FirebaseStorage firebaseStorage;
    private Button btnKembalikan, btnBackProfil;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);

        //intent kembali ke profil
        btnBackProfil = findViewById(R.id.btn_backprofil);
        btnBackProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MypostActivity.this, MainActivity.class));
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();


    }
}
