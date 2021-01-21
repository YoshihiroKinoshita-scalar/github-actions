package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private int userstatus;
}