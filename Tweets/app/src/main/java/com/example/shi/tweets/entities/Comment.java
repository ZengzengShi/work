package com.example.shi.tweets.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shi on 2018/4/13.
 */

public class Comment {

    @SerializedName("sender")
    private Sender mCommentSender;

    @SerializedName("content")
    private String mContent;

    public Comment(){};
}
