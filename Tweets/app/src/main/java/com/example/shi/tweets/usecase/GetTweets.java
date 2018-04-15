package com.example.shi.tweets.usecase;

import com.example.shi.tweets.data.DateSource;
import com.example.shi.tweets.data.TweetsRepository;
import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/14.
 */

public class GetTweets extends UseCase <GetTweets.GetTweetsRequest, GetTweets.GetTweetsResponse>{

    private TweetsRepository mRepository;

    public GetTweets(TweetsRepository repository){
        mRepository = repository;
    }


    @Override
    protected void executeUseCase(GetTweetsRequest requestValues) {
        mRepository.getTweets(requestValues.getUserName(), new DateSource.LoadCallBack() {
            @Override
            public void onLoaded(ArrayList<Tweet> tweets) {
                getUseCaseCallback().onSuccess(new GetTweetsResponse(tweets));
            }

            @Override
            public void onFail(String reason) {
                getUseCaseCallback().onError(reason);
            }
        });

    }

    public static final class GetTweetsRequest implements UseCase.RequestValues{
        private String userName;

        public String getUserName(){
            return userName;
        }

        public void setUserName(String name){
            userName = name;
        }

    }

    public static final class GetTweetsResponse implements UseCase.ResponseValue{

        private ArrayList<Tweet> mTweets;

        public GetTweetsResponse(ArrayList<Tweet> tweets){
            mTweets = tweets;
        }


        public ArrayList<Tweet> getResponse(){
            return mTweets;
        }

    }
}
