package com.example.myproject;

public class User
{
    public String email,FullName,city,Phone,bloodGroup;

    public User()
    {
    }

    public User(String email,String FullName,String city,String Phone,String bloodGroup )
    {
        this.email = email;
        this.FullName = FullName;
        this.city = city;
        this.Phone = Phone;
        this.bloodGroup = bloodGroup;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return FullName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        this.Phone = phone;
    }
    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}

