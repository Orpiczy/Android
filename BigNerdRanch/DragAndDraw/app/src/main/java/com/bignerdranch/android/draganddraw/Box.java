package com.bignerdranch.android.draganddraw;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

public class Box implements Parcelable {
    private PointF mOrigin;
    private PointF mCurrent;

    //    jaka jest różnica między point, a pointF? - pointF przechowuje koordynaty we floacie

    public Box(PointF origin){
        mOrigin = origin;
        mCurrent = origin;
    }

    public Box(Parcel parcel) {
        mOrigin = (PointF) parcel.readValue(PointF.class.getClassLoader());
        mCurrent = (PointF) parcel.readValue(PointF.class.getClassLoader());
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(mOrigin);
        parcel.writeValue(mCurrent);
    }

    public static final Parcelable.Creator<Box> CREATOR = new Parcelable.Creator<Box>(){

        @Override
        public Box createFromParcel(Parcel parcel) {
            return new Box(parcel);
        }

        @Override
        public Box[] newArray(int size) {
            return new Box[size];
        }
    };
}
