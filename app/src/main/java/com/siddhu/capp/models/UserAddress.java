package com.siddhu.capp.models;

/**
 * Created by baji_g on 7/25/2017.
 */
public class UserAddress {
    private String address;
    private String city;
    private String pinno;

    public UserAddress() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinno() {
        return pinno;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }
}
