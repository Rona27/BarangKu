package com.example.barangqu.Adapter;

import android.graphics.ColorSpace;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barangqu.Adapter.RecylerViewAdapter;
import com.example.barangqu.Model.Posts;
import com.example.barangqu.R;

public class BarangHolder extends RecyclerView.ViewHolder {

    public TextView tvNamaBarang;
    public TextView tvDeskripsi;
    public TextView btnOpen;


    public BarangHolder(@NonNull View itemView) {
        super(itemView);
        tvNamaBarang = itemView.findViewById(R.id.tv_judul_post);
        tvDeskripsi = itemView.findViewById(R.id.tv_deskrip_post);
        btnOpen = itemView.findViewById(R.id.btn_kunjungi);
    }

    public void bindToBarang(Posts posts, View.OnClickListener onClickListener){

        tvNamaBarang.setText(posts.namaBarang);
        tvDeskripsi.setText(posts.deskripsi);
        btnOpen.setOnClickListener(onClickListener);


    }
}
