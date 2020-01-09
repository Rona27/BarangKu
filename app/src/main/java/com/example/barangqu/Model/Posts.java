package com.example.barangqu.Model;

public class Posts {

    public String uid, time, date, postimage,namaPost, tglPost , description, profileimage, fullname;

    public Posts()
    {

    }

    public Posts(String uid, String time, String date, String postimage,String namaPost, String tglPost, String description, String profileimage, String fullname) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.postimage = postimage;
        this.namaPost = namaPost;
        this.tglPost = tglPost;
        this.description = description;
        this.profileimage = profileimage;
        this.fullname = fullname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getNamaPost(){
        return namaPost;
    }

    public void  setNamaPost(String namaPost){
        this.namaPost= namaPost;
    }

    public String getTglPost() {
        return tglPost;
    }

    public void setTglPost(String tglPost) {
        this.tglPost = tglPost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
