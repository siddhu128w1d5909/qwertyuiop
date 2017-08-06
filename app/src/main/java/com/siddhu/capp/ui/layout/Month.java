package com.siddhu.capp.ui.layout;

/**
 * Created by dhiman_da on 09/08/16.
 */
public class Month {
    private int mCode;
    private String mName;

    private Month() {

    }

    public Month(int code, String name) {
        mCode = code;
        mName = name;
    }

    public int getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }
}
