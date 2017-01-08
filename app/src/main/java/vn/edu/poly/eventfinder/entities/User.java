package vn.edu.poly.eventfinder.entities;

public class User {
    private String uid;
    private String email;
    private double userLatitude;
    private double userLongitude;
    private String phoneNumber;

    public User() {
    }

    public User(String uid, String email, double userLatitude, double userLongitude, String phoneNumber) {
        this.uid = uid;
        this.email = email;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
