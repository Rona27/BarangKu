package com.example.barangqu.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barangqu.Model.PostInformation;
import com.example.barangqu.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

public class InsertContentActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = CompleteProfilActitvity.class.getSimpleName();
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private ImageView imgPost;
    private EditText edtNamaBarang, edtTgl, edtDeskrip;
    private Button btnPost;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;

    private StorageReference storageReference;

    public InsertContentActivity(){

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                imgPost.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_content);

        auth= FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        edtNamaBarang = findViewById(R.id.edt_nama_barang);
        edtDeskrip = findViewById(R.id.edt_deskrip);
        btnPost = findViewById(R.id.btn_post);
        FirebaseUser user = auth.getCurrentUser();
        btnPost.setOnClickListener(this);
        imgPost = findViewById(R.id.img_barang);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postImage = new Intent();
                postImage.setType("image/*");
                postImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(postImage, "Pilih gambar."), PICK_IMAGE);



            }
        });


        //Intent back to profil
        ImageButton btnProfil = findViewById(R.id.btn_profil);

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsertContentActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void sendPostData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //Get "User UID" fromfirebase > Authentication > Users
        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());
        StorageReference imagereference = storageReference.child("ImagesPost").child(auth.getUid()).child("Post pic");
        UploadTask uploadTask = imagereference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InsertContentActivity.this, "Error : Upload foto Post", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Toast.makeText(InsertContentActivity.this, "Berhasil Upload foto Post", Toast.LENGTH_SHORT).show();





            }

        });



    }

    private void postInformation(){

//        Date date = new Date();
//        String strDateFormat = "hh:mm:ss a";
//        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
//        String formattedDate= dateFormat.format(date);

        String namaBarang = edtNamaBarang.getText().toString().trim();
        String deskripsi = edtDeskrip.getText().toString().trim();
        Date currentTime = Calendar.getInstance().getTime();
        PostInformation postInformation = new PostInformation(currentTime,namaBarang,deskripsi );
        FirebaseUser user = auth.getCurrentUser();
        databaseReference.child("Post").child(user.getUid()).setValue(postInformation);
        Toast.makeText(getApplicationContext(), "Informasi Post telah ditambahkan", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        if (v==btnPost){
            if (imagePath == null) {
                Toast.makeText(InsertContentActivity.this, "Berhasil Upload foto profil", Toast.LENGTH_SHORT).show();

                Drawable drawable = this.getResources().getDrawable(R.drawable.ic_boy);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_boy);


                postInformation();




                finish();
                startActivity(new Intent(InsertContentActivity.this, MainActivity.class));
            }
            else {

                postInformation();
                sendPostData();
                finish();
                startActivity(new Intent(InsertContentActivity.this, MainActivity.class));

            }
        }

    }



    public void openSelectedProfilPicturedialod(){

        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        TextView title = new TextView(this);
        title.setText("Post Picture");
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
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
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
