package com.bignerdranch.android.criminalintent;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mID;
    private String mTitle;
    private Date mDate;
    private String mSuspect;
    private boolean mSolved;



    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public Crime(){
        mID = UUID.randomUUID();
        mDate = new Date();
        mSolved = false;
    }

    public Crime(UUID uuid){
        mID = uuid;
        mDate = new Date();
        mSolved = false;
    }

    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getPhotoFileName(){
        return "IMG_"+getID().toString()+".jpg";
    }
}
