package com.example.shi.tweets.ui;

import android.graphics.Bitmap;

import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/14.
 */

public interface UiContract {

    interface ItweetsView extends BaseView<ItweetsPresenter>{
        public void updataTweets(ArrayList<Tweet> tweets);
        public void showErrorMsg(String errorMsg);

    }

    interface  ItweetsPresenter extends BasePresenter{

        public void loadAllTweets();
        public void loadNextScreen();
        public void loadImage(String url, UiLoadImageCallBack callBack);
    }

    interface UiLoadImageCallBack {
        public void onLoaded(Bitmap bitmap);
    }
}
