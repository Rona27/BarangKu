package com.example.barangqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SharedMemory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barangqu.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class MainActivity extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private TextView tvNim, tvNama;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView imgProfil;
    private FirebaseStorage firebaseStorage;
    private TextView tvEmail;
    private EditText edtNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent akunInten = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(akunInten);
                        break;

                    case R.id.action_account:
                        Toast.makeText(getApplicationContext(), "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }

                return true;
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        edtNama = findViewById(R.id.edt_nama);
        imgProfil = findViewById(R.id.img_profil);
        tvNim = findViewById(R.id.tv_nim);
        tvNama = findViewById(R.id.tv_nama);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());
        StorageReference storageReference = firebaseStorage.getReference();
        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".

        storageReference.child(auth.getUid()).child("Images").child("Profil pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                // ".fit().centerInside()" fits the entire image into the specified area.
                // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                Picasso.get().load(uri).fit().centerInside().into(imgProfil);
            }
        });

        if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        final FirebaseUser user= auth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userProfile = dataSnapshot.getValue(UserInformation.class);
                tvNim.setText(userProfile.getUsernim());
                tvNama.setText(userProfile.getUsernama());
                tvEmail = findViewById(R.id.tv_email);
                tvEmail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MainActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void buttonClickedEditNama(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_edit_nama, null);
        final EditText edtNama = alertLayout.findViewById(R.id.edt_nama);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Name edit");

        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = tvNama.getText().toString();
                UserInformation userInformation = new UserInformation();
                FirebaseUser user = auth.getCurrentUser();
                databaseReference.child(user.getUid()).setValue(userInformation);
                databaseReference.child(user.getUid()).setValue(userInformation);
                tvNama.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent inent = new Intent(this, LoginActivity.class);
        startActivity(inent);
    }
}

