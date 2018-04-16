package com.example.shi.tweets.ui;

import com.example.shi.tweets.data.TweetsRepository;
import com.example.shi.tweets.entities.Tweet;
import com.example.shi.tweets.filter.FilterFactory;
import com.example.shi.tweets.usecase.GetTweets;
import com.example.shi.tweets.usecase.LoadImage;
import com.example.shi.tweets.usecase.UseCase;
import com.example.shi.tweets.usecase.UseCaseHandler;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/14.
 */

public class TweetsPresenter implements UiContract.ItweetsPresenter {

    private final int SCREEN_DISPLAY_COUNT = 5;

    private UseCaseHandler mUseCaseHandler;
    private TweetsRepository mRepository;
    private GetTweets mGetTweetsUseCase;
    private LoadImage mLoadImageUserCase;
    private UiContract.ItweetsView mView;

    private ArrayList<Tweet> mTweets;
    private int mDisplayCount = 0;

    public TweetsPresenter(UseCaseHandler handler, TweetsRepository repository,
                           GetTweets getTweets, LoadImage loadImage, UiContract.ItweetsView view){

        mUseCaseHandler = handler;
        mRepository = repository;
        mGetTweetsUseCase = getTweets;
        mLoadImageUserCase = loadImage;
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
                mTweets = filtedTweets;

                ArrayList<Tweet> displayTweets = getDisplayTweets(mTweets);
                if(displayTweets != null){
                    mView.updataTweets(displayTweets);
                }else{
                    mView.showErrorMsg("The end of Tweets!");
                }
            }

            @Override
            public void onError(String errorMsg) {
                mView.showErrorMsg(errorMsg);

            }

        });

    }

    private ArrayList<Tweet> getDisplayTweets(ArrayList<Tweet> tweets){
        int begin = mDisplayCount * SCREEN_DISPLAY_COUNT;

        if(begin < tweets.size() - 1){
            ArrayList<Tweet> displayTweets = new ArrayList<>();
            int index = begin;
            while(index < Math.min(begin + SCREEN_DISPLAY_COUNT, tweets.size()) ){
                displayTweets.add(tweets.get(index));
                index++;
            }
            return displayTweets;
        }
        return null;
    }

    @Override
    public void loadNextScreen() {

    }

    @Override
    public void loadImage(String url, final UiContract.UiLoadImageCallBack callBack) {

        mUseCaseHandler.execute(mLoadImageUserCase, new LoadImage.LoadImageRequest(url),
                new UseCase.UseCaseCallback<LoadImage.LoadImageResponse>() {
            @Override
            public void onSuccess(LoadImage.LoadImageResponse response) {

                callBack.onLoaded(response.getBitmap());

            }

            @Override
            public void onError(String errorMsg) {
                mView.showErrorMsg(errorMsg);

            }
        });
    }
}
