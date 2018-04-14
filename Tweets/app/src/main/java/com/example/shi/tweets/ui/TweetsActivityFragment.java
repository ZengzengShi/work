package com.example.shi.tweets.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shi.tweets.R;
import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TweetsActivityFragment extends Fragment implements UiContract.ItweetsView{

    public TweetsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweets, container, false);
    }

    @Override
    public void setPresence(UiContract.ItweetsPresenter presence) {

    }

    @Override
    public void updataTweets(ArrayList<Tweet> tweets) {

    }
}
