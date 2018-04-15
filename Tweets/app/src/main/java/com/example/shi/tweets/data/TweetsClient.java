package com.example.shi.tweets.data;

import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shi on 2018/4/13.
 */

public interface TweetsClient {
    @GET("/user/{user}/tweets")
    Call<ArrayList<Tweet>> getTweets(
            @Path("user") String user
    );
}
