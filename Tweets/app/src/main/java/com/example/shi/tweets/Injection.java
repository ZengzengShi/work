package com.example.shi.tweets;

import com.example.shi.tweets.data.TweetsRepository;
import com.example.shi.tweets.usecase.GetTweets;
import com.example.shi.tweets.usecase.UseCaseHandler;

/**
 * Created by shi on 2018/4/14.
 */

public class Injection {
    public static UseCaseHandler providerUseCaseHandler(){
        return UseCaseHandler.getInstance();
    };

    public static TweetsRepository providerRepository(){
        return TweetsRepository.getInstance();
    };
    public static GetTweets providerGetTweetsUseCase(){
        return new GetTweets(providerRepository());
    };
}
