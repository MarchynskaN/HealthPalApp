package com.example.healthpal.ui.LabTest;

public class LabTest {
    private String title;
    private String details;
    private String price;

    public LabTest(String title, String details, String price) {
        this.title = title;
        this.details = details;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getPrice() {
        return price;
    }
}
