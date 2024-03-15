package com.example.ehnoapp;


public class UserHelperDatabase {
    private String fname;
    private String lname;
    private String sMessage;
    private String EPhone;
    private String city;
    private String Phone;
    private double lat,longitude,altiude;
    private float accuracy;

    public UserHelperDatabase() {
    }


    public UserHelperDatabase(String fname, String lname, String sMessage, String EPhone, String city, String phone, double lat, double longitude, double altiude, float accuracy) {
        this.fname = fname;
        this.lname = lname;
        this.sMessage = sMessage;
        this.EPhone = EPhone;
        this.city = city;
        Phone = phone;
        this.lat = lat;
        this.longitude = longitude;
        this.altiude = altiude;
        this.accuracy = accuracy;
    }

    public UserHelperDatabase(String phone, double lat, double longitude, double altiude, float accuracy) {
        Phone = phone;
        this.lat = lat;
        this.longitude = longitude;
        this.altiude = altiude;
        this.accuracy = accuracy;
    }

    public double getAltiude() {
        return altiude;
    }

    public void setAltiude(double altiude) {
        this.altiude = altiude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getsMessage() {
        return sMessage;
    }

    public void setsMessage(String sMessage) {
        this.sMessage = sMessage;
    }

    public String getEPhone() {
        return EPhone;
    }

    public void setEPhone(String EPhone) { this.EPhone = EPhone;}
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
