package com.example.shi.tweets.data;

import android.support.annotation.NonNull;

import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/14.
 */

public interface DateSource {

    interface LoadCallBack{
        void onLoaded(ArrayList<Tweet> tweets);
        void onFail(String reason);
    }

    void getTweets(@NonNull String user, @NonNull LoadCallBack callBack);

    public void destroy();
}
