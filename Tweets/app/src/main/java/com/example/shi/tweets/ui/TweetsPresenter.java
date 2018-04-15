package com.example.shi.tweets.ui;

import com.example.shi.tweets.data.TweetsRepository;
import com.example.shi.tweets.usecase.GetTweets;
import com.example.shi.tweets.usecase.UseCase;
import com.example.shi.tweets.usecase.UseCaseHandler;

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
                mView.updataTweets(response.getResponse());
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
