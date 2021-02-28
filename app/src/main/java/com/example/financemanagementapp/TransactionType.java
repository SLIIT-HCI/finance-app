package com.example.financemanagementapp;

public class TransactionType {

    private String type;
    private String amount;

    public TransactionType(String type) {
        this.type = type;
    }

    public TransactionType(String type, String amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String category) {
        this.type = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
