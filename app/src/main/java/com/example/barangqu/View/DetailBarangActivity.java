package com.example.barangqu.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barangqu.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DetailBarangActivity extends AppCompatActivity {

    TextView tvJudulBarang, tvDeskripsi, tvTglPost;
    ImageView imgBarang;

    private void initializeWidgets(){
        tvJudulBarang= findViewById(R.id.tv_judul_post);
        tvDeskripsi= findViewById(R.id.tv_deskrip_post);
        tvTglPost= findViewById(R.id.tv_tgl_post);

    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        initializeWidgets();

        //RECEIVE DATA FROM ITEMSACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String name=i.getExtras().getString("JUDUL_KEY");
        String description=i.getExtras().getString("DESKRIPSI_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");

        //SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        tvJudulBarang.setText(name);
        tvDeskripsi.setText(description);
        tvTglPost.setText("DATE: "+getDateToday());
//        Picasso.get(this)
//                .load(imageURL)
//                .placeholder(R.drawable.ic_buy)
//                .fit()
//                .centerCrop()
//                .into(imgBarang);

    }

}
