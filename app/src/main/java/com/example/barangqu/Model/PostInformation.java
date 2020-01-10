package com.example.barangqu.Model;

import java.util.Date;

public class PostInformation {

    private Date currentTime;
    private String namaBarang;
    private String deskripsi;
//    private String downloadUrl;

    public PostInformation(){


    }

    public PostInformation(Date currentTime, String namaBarang, String deskripsi){
        this.currentTime = currentTime;
        this.namaBarang = namaBarang;
        this.deskripsi = deskripsi;
//        this.downloadUrl = downloadUrl;
    }


    public String getNamaBarang() {
        return namaBarang;
    }

//    public void setNamaBarang(String namaBarang) {
//        this.namaBarang = namaBarang;
//    }

    public String getDeskripsi() {
        return deskripsi;
    }

//    public void setDeskripsi(String deskripsi) {
//        this.deskripsi = deskripsi;
//    }

    public Date getCurrentTime() {
        return currentTime;
    }

//    public String getDownloadUrl() {
//        return downloadUrl;
//    }

//    public void setCurrentTime(Date currentTime) {
//        this.currentTime = currentTime;
//    }

//    public Date getFormattedDate() {
//        return formattedDate;
//    }
//
//    public void setFormattedDate(Date formattedDate) {
//        this.formattedDate = formattedDate;
//    }
}
