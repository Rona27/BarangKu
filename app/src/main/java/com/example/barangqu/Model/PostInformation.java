package com.example.barangqu.Model;

import java.util.Date;

public class PostInformation {

    private Date currentTime;
    private String namaBarang;
    private String deskripsi;

    public PostInformation(String currentTime, String namaBarang, String deskripsi){


    }

    public PostInformation(Date currentTime, String namaBarang, String deskripsi){
        this.currentTime = currentTime;
        this.namaBarang = namaBarang;
        this.deskripsi = deskripsi;
    }


    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

//    public Date getFormattedDate() {
//        return formattedDate;
//    }
//
//    public void setFormattedDate(Date formattedDate) {
//        this.formattedDate = formattedDate;
//    }
}
