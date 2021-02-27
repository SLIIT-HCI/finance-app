package com.example.financemanagementapp;

public class TransactionCategories {

    private String category;
    private String amount;

    public TransactionCategories(String category, String amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
