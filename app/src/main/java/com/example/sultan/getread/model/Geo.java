package com.example.sultan.getread.model;

/**
 * Created by Sultan on 3/3/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Geo implements Parcelable {

    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;

    protected Geo(Parcel in) {
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<Geo> CREATOR = new Creator<Geo>() {
        @Override
        public Geo createFromParcel(Parcel in) {
            return new Geo(in);
        }

        @Override
        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };

    public String getGeo() {
        return String.format("\nlat: %1$s, lng: %2$s" ,lat, lng);
    }
    public String getLat() {
        return lat;
    }
    public String getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(lng);
    }
}
