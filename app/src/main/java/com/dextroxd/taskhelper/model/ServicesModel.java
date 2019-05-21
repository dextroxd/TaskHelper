package com.dextroxd.taskhelper.model;

public class ServicesModel
{
    private String mTitle;
    private String mDesc;
    private String mImg;

    public ServicesModel(String mTitle, String mDesc, String mImg) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mImg = mImg;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getmImg() {
        return mImg;
    }
}
