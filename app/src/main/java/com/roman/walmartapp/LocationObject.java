package com.roman.walmartapp;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationObject implements Parcelable {
    double mLat;
    double mLong;


    LocationObject(double locLat, double locLong) {
        this.mLat = locLat;
        this.mLong = locLong;
    }

    protected LocationObject(Parcel in) {
        mLat = in.readDouble();
        mLong = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLat);
        dest.writeDouble(mLong);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LocationObject> CREATOR = new Parcelable.Creator<LocationObject>() {
        @Override
        public LocationObject createFromParcel(Parcel in) {
            return new LocationObject(in);
        }

        @Override
        public LocationObject[] newArray(int size) {
            return new LocationObject[size];
        }
    };
}
