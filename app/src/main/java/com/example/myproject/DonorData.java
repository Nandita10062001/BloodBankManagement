package com.example.myproject;

import java.util.Dictionary;

public class DonorData {

    private String Name, Contact, UID;

    public DonorData(){

    }

    public DonorData( String Name, String Contact, String UID) {

        this.Name = Name;
        this.Contact = Contact;
        this.UID = UID;

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String donorName) {
        this.Name = Name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String donorContact) {
        this.Contact = Contact;
    }
}

