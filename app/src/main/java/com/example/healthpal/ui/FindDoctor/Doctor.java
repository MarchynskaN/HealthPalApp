package com.example.healthpal.ui.FindDoctor;

public class Doctor {
    private String name;
    private String address;
    private String experience;
    private String number;
    private String rate;

    public Doctor(String name, String address, String experience, String number, String rate) {
        this.name = name;
        this.address = address;
        this.experience = experience;
        this.number = number;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
