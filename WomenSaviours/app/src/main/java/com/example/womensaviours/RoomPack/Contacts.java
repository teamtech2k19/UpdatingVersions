package com.example.womensaviours.RoomPack;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts_table")
public class Contacts {

    @PrimaryKey
    @NonNull
    String number;

    String name;
    String mail;
    String en1;
    String en2;
    String en3;
    String en4;
    String en5;


    double lon ,lat;
    public Contacts(){}

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEn1() {
        return en1;
    }

    public void setEn1(String en1) {
        this.en1 = en1;
    }

    public String getEn2() {
        return en2;
    }

    public void setEn2(String en2) {
        this.en2 = en2;
    }

    public String getEn3() {
        return en3;
    }

    public void setEn3(String en3) {
        this.en3 = en3;
    }

    public String getEn4() {
        return en4;
    }

    public void setEn4(String en4) {
        this.en4 = en4;
    }

    public String getEn5() {
        return en5;
    }

    public void setEn5(String en5) {
        this.en5 = en5;
    }







    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }









}
