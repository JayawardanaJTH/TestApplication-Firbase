package com.example.testapplication_firebase;

public class Student {

    private String ID;
    private String Name;
    private String Address;
    private Integer conNum;

    public Student() {
    }

    public Student(String ID, String name, String address, Integer conNo) {
        this.ID = ID;
        Name = name;
        Address = address;
        this.conNum = conNo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getConNo() {
        return conNum;
    }

    public void setConNo(Integer conNum) {
        this.conNum = conNum;
    }
}
