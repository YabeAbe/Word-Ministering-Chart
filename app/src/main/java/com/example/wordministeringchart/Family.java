package com.example.wordministeringchart;

public class Family {
    // Family general information
    public String familyName;
    public String address;
    public String memberKey;

    public Family() {}

    // Getter and Setter method
    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMemberKey(){
        return memberKey;
    }
    public void setMemberKey(String memberKey) {
        this.memberKey = memberKey;
    }
}
