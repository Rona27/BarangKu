package com.example.barangqu.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barangqu.Model.Posts;
import com.example.barangqu.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecylerViewAdapter  extends RecyclerView.Adapter<RecylerViewAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<Posts> posts;
    private OnItemClickListener mListener;

    public RecylerViewAdapter(Context context, List<Posts> uploads) {
        mContext = context;
        posts = uploads;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_barang, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Posts currentPost = posts.get(position);

//        holder.judulBarang.setText(currentPost.getNamaBarang());
//        holder.deskripsiBarang.setText(currentPost.getDeskripsi());
        holder.tglPost.setText((getDateToday()));
//        Picasso.get(mContext)
//                .load(currentPost.getImageURL())
//                .placeholder(R.drawable.ic_buy)
//                .fit()
//                .centerCrop()
//                .into(holder.imgBarang);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {


        public TextView judulBarang,deskripsiBarang,tglPost;
        public ImageView imgBarang;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            judulBarang = itemView.findViewById(R.id.tv_judul_post);
            deskripsiBarang = itemView.findViewById(R.id.tv_deskrip_post);
            tglPost = itemView.findViewById(R.id.tv_tgl_post);
//            imgBarang = itemView.findViewById(R.id.img_barang_post);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener =  listener;
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }
}
