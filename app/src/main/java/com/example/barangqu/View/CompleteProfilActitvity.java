package com.example.barangqu.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barangqu.Model.UserInformation;
import com.example.barangqu.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CompleteProfilActitvity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = CompleteProfilActitvity.class.getSimpleName();
    Button btnsave;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private EditText edtNim, edtNama;
    private TextView tvEmail;
    private ImageView imgProfil;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;

    public CompleteProfilActitvity(){

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                imgProfil.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profil_actitvity);

        auth= FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        edtNim = findViewById(R.id.edt_nim);
        edtNama = findViewById(R.id.edt_nama);
        btnsave = findViewById(R.id.btn_save);
        FirebaseUser user = auth.getCurrentUser();
        btnsave.setOnClickListener(this);
        tvEmail = findViewById(R.id.tv_email);
        tvEmail.setText(user.getEmail());
        imgProfil = findViewById(R.id.img_profil);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilIntent = new Intent();
                profilIntent.setType("image/*");
                profilIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profilIntent, "Pilih gambar."), PICK_IMAGE);
            }
        });
    }

    private void userInformation(){
        String nim = edtNim.getText().toString().trim();
        String nama = edtNama.getText().toString().trim();
        UserInformation userinformation = new UserInformation(nim, nama);
        FirebaseUser user = auth.getCurrentUser();
        databaseReference.child("User").child(user.getUid()).setValue(userinformation);
        Toast.makeText(getApplicationContext(), "Informasi Profil telah ditambahkan", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view==btnsave ) {
            if (imagePath == null) {

                Toast.makeText(CompleteProfilActitvity.this, "Berhasil Upload foto profil", Toast.LENGTH_SHORT).show();

                Drawable drawable = this.getResources().getDrawable(R.drawable.ic_boy);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_boy);

                //membuka
//                openSelectedProfilPicturedialod();
                userInformation();


                finish();
                startActivity(new Intent(CompleteProfilActitvity.this, HomeActivity.class));
            }

            else {


                userInformation();
                sendUserData();
                finish();
                startActivity(new Intent(CompleteProfilActitvity.this, HomeActivity.class));

            }
        }
    }

    private void sendUserData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //Get "User UID" fromfirebase > Authentication > Users
        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());
        StorageReference imagereference = storageReference.child("ImagesUser").child(auth.getUid()).child("Profil pic");
        UploadTask uploadTask = imagereference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CompleteProfilActitvity.this, "Error : Upload foto Profil", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CompleteProfilActitvity.this, "Berhasil Upload foto profil", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void openSelectedProfilPicturedialod(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        TextView title = new TextView(this);
        title.setText("Profil Picture");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);
        TextView msg = new TextView(this);
        msg.setText("Silahkan pilih Foto profil anda \n Klik logo profil untuk memilih");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

    }


}
