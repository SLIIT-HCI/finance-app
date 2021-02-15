package com.example.financemanagementapp;

public class SMSProvider {

    private String id;
    private String nameSMS;
    private String contactSMS;

    public SMSProvider() {
    }

    public SMSProvider(String id, String nameSMS, String contactSMS) {
        this.id = id;
        this.nameSMS = nameSMS;
        this.contactSMS = contactSMS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameSMS() {
        return nameSMS;
    }

    public void setNameSMS(String nameSMS) {
        this.nameSMS = nameSMS;
    }

    public String getContactSMS() {
        return contactSMS;
    }

    public void setContactSMS(String contactSMS) {
        this.contactSMS = contactSMS;
    }
}
