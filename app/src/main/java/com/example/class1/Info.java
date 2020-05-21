package com.example.class1;

public class Info {
    String name,school,parent,occParent,address,mobNo,email,password,batch;

    public Info() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOccParent() {
        return occParent;
    }

    public void setOccParent(String occParent) {
        this.occParent = occParent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String board) {
        this.batch = batch;
    }


    public Info(String name, String school, String parent, String occParent, String address, String mobNo, String email, String password, String batch) {
        this.name = name;
        this.school = school;
        this.parent = parent;
        this.occParent = occParent;
        this.address = address;
        this.mobNo = mobNo;
        this.email = email;
        this.password = password;
        this.batch = batch;
    }
}
