package com.example.shi.tweets.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/13.
 */

public class Tweet {
    @SerializedName("content")
    private String mContent;

    @SerializedName("images")
    private String [] mImagesUrl;

    @SerializedName("sender")
    private Sender mSender;

    @SerializedName("comments")
    private ArrayList<Comment> mComments;

    public Tweet(){}

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("content: " +  mContent);
        stringBuffer.append("sender: " + mSender.toString());
        return stringBuffer.toString();
    }
}
