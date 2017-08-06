package com.siddhu.capp.models;

/**
 * Created by siddhu on 4/1/2017.
 */

public class ServerRequest {
    private String operation;
    private Student user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(Student user) {
        this.user = user;
    }
}
