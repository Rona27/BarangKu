package com.example.barangqu.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Posts {


    public String namaBarang;
    public String deskripsi;
    private int position;

    public Posts(){



   }

    public Posts(String namaBarang, String deskripsi) {
        this.namaBarang = namaBarang;
        this.deskripsi = deskripsi;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("namaBarang", namaBarang);
        result.put("deskripsi", deskripsi);
        return result;
    }
}
