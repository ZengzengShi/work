package com.example.shi.tweets.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shi on 2018/4/13.
 */

public class Sender {

    @SerializedName("username")
    private String mName;

    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("nick")
    private String mNick;

    public Sender(String name, String nick, String avatar){
        mName = name;
        mNick = nick;
        mAvatar = avatar;
    }

    public String getName(){
        return mName;
    }

    public String getAvatar(){
        return mAvatar;
    }

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("name: " + mName);
        stringBuffer.append("nick: " + mNick);
        stringBuffer.append("avatar: " + mAvatar);
        return stringBuffer.toString();
    }
}
