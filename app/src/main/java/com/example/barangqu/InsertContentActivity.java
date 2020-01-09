package com.example.barangqu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class InsertContentActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private  Button btnPost;
    private ImageButton btnPostImage;
    private EditText edtNamaPost, edtTanggalPost, edtDeskrip;

    private static final int Gallery_Pick = 1;
    private Uri ImageUri;
    private String NamaBarang;
    private String TanggalPost;
    private String Description;


    private StorageReference PostsImagesRefrence;
    private DatabaseReference UsersRef, PostsRef;
    private FirebaseAuth mAuth;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_content);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        PostsImagesRefrence = FirebaseStorage.getInstance().getReference();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");


        btnPostImage = (ImageButton) findViewById(R.id.img_btn_barang);
        btnPost = findViewById(R.id.btn_post);
        edtNamaPost =(EditText) findViewById(R.id.edt_nama_barang);
        edtTanggalPost = findViewById(R.id.edt_tgl);
        edtDeskrip = findViewById(R.id.edt_deskrip);
        progressDialog = new ProgressDialog(this);


        btnPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePostInfo();
            }
        });

    }



    private void  ValidatePostInfo(){
        NamaBarang = edtNamaPost.getText().toString();
        TanggalPost = edtTanggalPost.getText().toString();
        Description = edtDeskrip.getText().toString();
        if (ImageUri == null) {
            Toast.makeText(getApplicationContext(), "Silahkan Pilih Foto", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(NamaBarang))
        {
            Toast.makeText(getApplicationContext(),"Apa Nama Barang yanag anda post", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(TanggalPost))
        {
            Toast.makeText(getApplicationContext(), "Kapan Anda post", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(getApplicationContext(), "Silahkan deskripsikan", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Add New Post");
            progressDialog.setMessage("Please wait, while we are updating your new post...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(true);

            StoringImageToFirebaseStorage();
        }

    }

    private void StoringImageToFirebaseStorage(){
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;


        StorageReference filePath = PostsImagesRefrence.child("Post Images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    Task<Uri> downloadUrl = task.getResult().getMetadata().getReference().getDownloadUrl();
                    Toast.makeText(getApplicationContext(), "image uploaded successfully to Storage...", Toast.LENGTH_SHORT).show();

                    SavingPostInformationToDatabase();

                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), "Error occured: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void SavingPostInformationToDatabase(){
        UsersRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String userFullName = dataSnapshot.child("User").child("nama").getValue().toString();
                    String userProfilImage = dataSnapshot.child("Profil pic").getValue().toString();

                    HashMap postMap = new HashMap();
                    postMap.put("uid", current_user_id);
                    postMap.put("date", saveCurrentDate);
                    postMap.put("time", saveCurrentTime);
//                    postMap.put("namabarang", edtNamaPost);
//                    postMap.put("tanggalPost", edtTanggalPost);
                    postMap.put("deskripsi", edtDeskrip);
                    postMap.put("postImage", downloadUrl);
                    postMap.put("profilImage", userProfilImage);
                    postMap.put("fullname", userFullName);

                    PostsRef.child(current_user_id + postRandomName).updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful()){
                                        sendUserToMainActivity();
                                        Toast.makeText(getApplicationContext(), "Posting Baru Succes", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "gagal update", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }

                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            btnPostImage.setImageURI(ImageUri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            sendUserToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }



    private void sendUserToMainActivity()
    {
        Intent mainIntent = new Intent(InsertContentActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
