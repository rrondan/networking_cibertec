package com.rrondan.clasenetworking.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Post {
    private int userId;
    private int id;
    private String title;

    @SerializedName("body")
    private String text;

    @NonNull
    @Override
    public String toString() {
        return "Post{ " +
                "title: '" + title + "'" +
                ",body: '" +  text + "'" +
                ",userId: '" + userId + "'" +
                ",id: '" + id + "'" +
                "}";
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
