package com.example.healthpal.ui.BuyMedicine;

public class Medicine {
    private String title;
    private String details;
    private String price;

    public Medicine(String title, String details, String price) {
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
