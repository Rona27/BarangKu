package com.example.barangqu.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barangqu.Model.PostInformation;
import com.example.barangqu.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MypostActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView tvJudulpost, tvDeskrip;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView imgBarangPost;
    private FirebaseStorage firebaseStorage;
    private Button btnKembali;
     private  ImageButton  btnBackProfil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);

        btnKembali = findViewById(R.id.btn_kmbli);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

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

        databaseReference = FirebaseDatabase.getInstance().getReference();
        imgBarangPost = findViewById(R.id.img_post);
        tvJudulpost = findViewById(R.id.tv_post_judul);
        tvDeskrip = findViewById(R.id.tv_post_deskrip);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        auth = FirebaseAuth.getInstance();

        //        databaseReference.child("User").child(auth.getUid()).addValueEventListener();
        StorageReference storageReference = firebaseStorage.getReference();
        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".

        storageReference.child("ImagesPost").child(auth.getUid()).child("Post pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uriP) {


                Picasso.get().load(uriP).fit().centerInside().into(imgBarangPost);
            }
        });

//        if (auth.getCurrentUser() == null) {
//            finish();
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        }
//
//        final FirebaseUser user= auth.getCurrentUser();
        databaseReference.child("Post").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                PostInformation postInfo = dataSnapshot.getValue(PostInformation.class);
                tvJudulpost.setText(postInfo.getNamaBarang());
                tvDeskrip.setText(postInfo.getDeskripsi());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MypostActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }


        });


    }

    private void deleteData() {

        auth = FirebaseAuth.getInstance();
        AlertDialog.Builder alert = new AlertDialog.Builder(
                MypostActivity.this);
        alert.setTitle("Kembalikan Barang");
        alert.setMessage("Apakah Barang sudah dikembalikan ?");
        Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = firebaseDatabase.getReference().child("Post").child(auth.getUid());
                databaseReference.removeValue();
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference deleteImage =firebaseStorage.getReference().child("ImagesPost").child(auth.getUid());
                deleteImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Image Berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }


}
