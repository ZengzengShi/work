package com.example.shi.tweets.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shi on 2018/4/15.
 */

public class ImageUrl {

    public ImageUrl(String url){
        mUrl = url;
    }

    @SerializedName("url")
    String mUrl;


    public String getStrUrl(){
        return mUrl;
    }

    @Override
    public String toString(){
        return mUrl;
    }
}
