package com.example.barangqu;

public class User {

    String nim;
    String nama;
    String email;
    String password;





    public User() {

    }


    public String getNim() {
        return nim;
    }

    public String getPassword(){
        return password;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
