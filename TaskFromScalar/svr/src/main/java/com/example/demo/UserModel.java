package com.example.demo;

public class UserModel {
    private String id = "";
    private String username = "";
    private String firstname = "";
    private String lastname = "";
    private String email = "";
    private String password = "";
    private String phone = "";
    private int userstatus = 0;

    public UserModel() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username; }

    public String getUsername() {
        return username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setUserstatus(int userstatus) {
        this.userstatus = userstatus;
    }

    public int getUserstatus() {
        return userstatus;
    }
}