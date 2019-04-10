package com.example.amma.cir2;

public class User {
    public String registerNumber, email;

    public User(){}
    public User(String registerNumber, String email) {
        this.registerNumber = registerNumber;
        this.email = email;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
