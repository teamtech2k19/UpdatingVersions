package com.example.safewomen.ui.feedback;

import com.google.firebase.auth.FirebaseUser;

class Pojo {
    String rating;
    String user;
    String sugg;


    public String getSugg() {
        return sugg;
    }

    public void setSugg(String sugg) {
        this.sugg = sugg;
    }




    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Pojo(String rating, String currentUser,String sugg) {
        this.rating = rating;
        this.user = currentUser;
        this.sugg = sugg;
    }
    public Pojo(){

    }
}
