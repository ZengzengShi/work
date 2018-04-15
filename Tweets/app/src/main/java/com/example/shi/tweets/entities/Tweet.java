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
    private ArrayList<ImageUrl> mImagesUrl;

    @SerializedName("sender")
    private Sender mSender;

    @SerializedName("comments")
    private ArrayList<Comment> mComments;

    @SerializedName("error")
    private String errorMsg;

    public Tweet(String content,
                 ArrayList<ImageUrl> images,
                 Sender sender, ArrayList<Comment> comments){
        mSender = sender;
        mContent = content;
        mComments = comments;
        mImagesUrl = images;
    }

    public String getSenderName(){
        if(mSender != null) {
            return mSender.getName();
        }
        return null;
    }

    public String getContent(){
        return mContent;
    }

    public ArrayList<Comment> getComments(){
        return mComments;
    }

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("content: " +  mContent);
        if(mSender != null) {
            stringBuffer.append("sender: " + mSender.toString());
        }
        stringBuffer.append("error: " + errorMsg);
        return stringBuffer.toString();
    }
}
