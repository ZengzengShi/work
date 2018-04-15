package com.example.shi.tweets.ui;

import com.example.shi.tweets.data.TweetsRepository;
import com.example.shi.tweets.entities.Tweet;
import com.example.shi.tweets.filter.FilterFactory;
import com.example.shi.tweets.usecase.GetTweets;
import com.example.shi.tweets.usecase.UseCase;
import com.example.shi.tweets.usecase.UseCaseHandler;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/14.
 */

public class TweetsPresenter implements UiContract.ItweetsPresenter {

    private UseCaseHandler mUseCaseHandler;
    private TweetsRepository mRepository;
    private GetTweets mGetTweetsUseCase;
    private UiContract.ItweetsView mView;

    public TweetsPresenter(UseCaseHandler handler, TweetsRepository repository,
                           GetTweets getTweets, UiContract.ItweetsView view){

        mUseCaseHandler = handler;
        mRepository = repository;
        mGetTweetsUseCase = getTweets;
        mView = view;

        mView.setPresence(this);
    }

    @Override
    public void start() {
        loadAllTweets();
    }

    @Override
    public void loadAllTweets() {

        GetTweets.GetTweetsRequest request = new GetTweets.GetTweetsRequest();
        request.setUserName("jsmith");
        mUseCaseHandler.execute(mGetTweetsUseCase, request, new UseCase.UseCaseCallback<GetTweets.GetTweetsResponse>() {
            @Override
            public void onSuccess(GetTweets.GetTweetsResponse response) {
                ArrayList<Tweet> filtedTweets =
                        FilterFactory.getFilter(
                                FilterFactory.VALIDE_TWEETS_FILTER).filter(response.getResponse());
                mView.updataTweets(filtedTweets);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showErrorMsg(errorMsg);

            }

        });

    }

    @Override
    public void loadNextScreen() {

    }
}
