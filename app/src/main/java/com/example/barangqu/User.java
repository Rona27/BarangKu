package com.example.barangqu;

public class User {

    private String nim;
    private String nama;
    private String email;

    public User(String nim, String nama, String email) {
        this.setNim(nim);
        this.setNama(nama);
        this.setEmail(email);
    }

    public User() {

    }


    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
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
