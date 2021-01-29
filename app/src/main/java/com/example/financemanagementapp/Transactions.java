package com.example.financemanagementapp;

public class Transactions {

    private String id;
    private float amount;
    private String transactionType;
    private String date;
    private String time;
    private String category;
    private String account;
    private String schedule;
    private String notes;

    public Transactions() {
    }

    public Transactions(String id, float amount, String transactionType, String date, String time, String category, String account, String schedule, String notes) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;
        this.time = time;
        this.category = category;
        this.account = account;
        this.schedule = schedule;
        this.notes = notes;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
