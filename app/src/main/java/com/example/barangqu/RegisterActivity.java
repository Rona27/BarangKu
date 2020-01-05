package com.example.barangqu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtNim, edtNama, edtEmail, edtPassword;
    private Button btnLogin, btnDaftar;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        edtNim = findViewById(R.id.edt_nim);
        edtNama = findViewById(R.id.edt_nama);
        edtEmail= findViewById(R.id.edt_email);
        edtPassword= findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnDaftar = findViewById(R.id.btn_daftar);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
//                final String nim = edtNim.getText().toString().trim();
//                final String nama = edtNama.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Masukan Alamat email anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Masukan Password anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password harus lebih dari 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //CREATE USER
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()){

//                                    addUserToDatabase(email, password, nim, nama);
                                    startActivity(new Intent(RegisterActivity.this, CompleteProfilActitvity.class));

                                }
                                else {


                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


            }
        });
    }

//    private void addUserToDatabase(String email,String password, String nim, String nama){
//
//        User user = new User();
//
//        user.setEmail(email);
//        user.setPassword(password);
//        user.setNim(nim);
//        user.setNama(nama);
//
//        FirebaseDatabase.getInstance().getReference("users").push().setValue(user);
//
//    }

    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
