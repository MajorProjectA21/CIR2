package com.example.amma.cir2;

public class studentProfileDetails {
    private String FullName, Gender, DOB, Age, Bloodgrp, Martial_Status, FathersName, Address, Religion, Nationality, Phone_Number ;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBloodgrp() {
        return Bloodgrp;
    }

    public void setBloodgrp(String bloodgrp) {
        Bloodgrp = bloodgrp;
    }

    public String getMartial_Status() {
        return Martial_Status;
    }

    public void setMartial_Status(String martial_Status) {
        Martial_Status = martial_Status;
    }

    public String getFathersName() {
        return FathersName;
    }

    public void setFathersName(String fathersName) {
        FathersName = fathersName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public studentProfileDetails(){}
    public studentProfileDetails(String fullName, String gender, String DOB, String age, String bloodgrp, String martial_Status, String fathersName, String address, String religion, String nationality, String phone_Number) {
        FullName = fullName;
        Gender = gender;
        this.DOB = DOB;
        Age = age;
        Bloodgrp = bloodgrp;
        Martial_Status = martial_Status;
        FathersName = fathersName;
        Address = address;
        Religion = religion;
        Nationality = nationality;
        Phone_Number = phone_Number;
    }
}
