package com.example.sultan.getread.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Sultan on 2/24/2017.
 */

public class User implements Parcelable{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("address")
    private Address address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("website")
    private String website;
    @SerializedName("company")
    private Company company;


    protected User(Parcel in) {
        name = in.readString();
        username = in.readString();
        email = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        phone = in.readString();
        website = in.readString();
        company = in.readParcelable(Company.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUsername() {
        return "Username: " + username;
    }
    public String getEmail() {
        return "Email: " + email;
    }
    public Address getAddress() {
        return address;
    }
    public String getPhone() {
        return "Phone: " + phone;
    }
    public String getWebsite() {
        return "Website: "+ website;
    }
    public Company getCompany() {
        return company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeParcelable(address, flags);
        dest.writeString(phone);
        dest.writeString(website);
        dest.writeParcelable(company, flags);
    }
}
