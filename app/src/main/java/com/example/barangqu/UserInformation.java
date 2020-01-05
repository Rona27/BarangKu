package com.example.barangqu;

public class UserInformation {

    public String nim;
    public String nama;
    public String email;
    public String password;


    public UserInformation() {

    }

    public UserInformation(String nim, String nama) {
        this.nim = nim ;
        this.nama = nama;
    }


    public String getUsernim(){
        return nim;
    }

    public String getUsernama(){
        return nama;
    }
}
