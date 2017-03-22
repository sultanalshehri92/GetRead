package com.example.sultan.getread.model;

/**
 * Created by Sultan on 3/8/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Post implements Parcelable {

    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    protected Post(Parcel in) {
        userId = in.readInt();
        id = in.readInt();
        title = in.readString();
        body = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };


    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return "Title:\n" + title;
    }

    public String getBody() {
        return "\nBody:\n" + body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(body);
    }
}