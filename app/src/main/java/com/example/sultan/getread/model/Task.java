package com.example.sultan.getread.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sultan on 3/8/2017.
 */

public class Task {
    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("completed")
    private Boolean completed;

    public int getUserId() {
        return userId;
    }
    public int getId() {
        return  id;
    }
    public String getTitle() {
        return title;
    }
    public Boolean getCompleted() {
        return completed;
    }
    public String getCompletedStatus() {
        if (completed)
            return "Done";
        else
            return "Todo";
    }

}
