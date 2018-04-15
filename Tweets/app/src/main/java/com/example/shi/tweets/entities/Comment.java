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

    public Comment(String cotent, Sender sender){
        mContent = cotent;
        mCommentSender = sender;
    };

    public String getSenderName(){
        if(mCommentSender != null) {
            return mCommentSender.getName();
        }
        return null;
    }

    public String getContent(){
        return mContent;
    }

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("mContent: " + mContent);
        if(mCommentSender != null) {
            stringBuffer.append("comment sender: " + mCommentSender.toString());
        }
        return stringBuffer.toString();
    }
}
