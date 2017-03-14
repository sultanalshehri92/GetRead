package com.example.sultan.getread.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sultan on 3/8/2017.
 */

public class Task {
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("completed")
    private Boolean completed;

    public String getUserId() {
        return "#"+ userId;
    }
    public String getId() {
        return "#"+ id;
    }
    public String getTitle() {
        return title;
    }
    public String getCompleted() {
        if (completed)
            return "done";
        else
            return "todo";
    }
}
