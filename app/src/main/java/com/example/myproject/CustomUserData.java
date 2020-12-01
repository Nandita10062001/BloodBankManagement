package com.example.myproject;

import java.io.Serializable;

public class CustomUserData implements Serializable
{
private String FullName, bloodGroup,Phone,address,Time,Date;

public CustomUserData()
{

}

public CustomUserData(String name,String bloodgroup,String contact,String city,String time,String date)
{
    FullName=name;
    bloodGroup=bloodgroup;
    Phone=contact;
    address=city;
    Time=time;
    Date=date;
}

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
