package com.example.wordministeringchart;

import java.util.ArrayList;
import java.util.List;

public class Family {
    // Family general information
    public String familyName;
    public String address;
    public ArrayList<String> familyMemberKeyArray;

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
    public ArrayList<String> getFamilyMemberKeyArray() {
        //if (familyMemberKeyArray != null) {
            return familyMemberKeyArray;
        //} else {
            //return null;
        //}

    }
    public void setFamilyMemberKeyList(ArrayList<String> familyMemberKeyArray) {
        this.familyMemberKeyArray = familyMemberKeyArray;
    }
}
