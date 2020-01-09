package com.example.barangqu.Model;

public class PostInformation {

    private String namaBarang;
    private String deskripsi;

    public PostInformation(){

    }

    public PostInformation(String namaBarang, String deskripsi){
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
}
