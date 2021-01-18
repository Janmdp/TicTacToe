package com.example.tictactoeapi.Models;

public class AuthRequest {
    private String userName;
    private String password;

    public String getUserName() { return userName; }
    public String getPassword() { return password; }

    public AuthRequest(){

    }

    public AuthRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
