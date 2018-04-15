package com.example.shi.tweets.ui;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shi.tweets.R;
import com.example.shi.tweets.entities.Comment;
import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TweetsActivityFragment extends Fragment implements UiContract.ItweetsView{

    private UiContract.ItweetsPresenter mPresenter;

    private TextView mEmptyView;

    private LinearLayout mTweetsContainer;
    private LayoutInflater mInflater;

    private Context mContext;

    public TweetsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tweets, container, false);
        mInflater = inflater;
        mEmptyView = (TextView) rootView.findViewById(R.id.empty_view);
        mTweetsContainer = (LinearLayout) rootView.findViewById(R.id.tweet_container);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public void onResume() {

        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresence(UiContract.ItweetsPresenter presence) {
        mPresenter = presence;
    }

    @Override
    public void updataTweets(ArrayList<Tweet> tweets) {
        if (tweets == null || tweets.size() == 0){
            mEmptyView.setVisibility(View.VISIBLE);
        }else{
            mEmptyView.setVisibility(View.GONE);
            showTweets(tweets);
        }

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    void showTweets(ArrayList<Tweet> tweets){
        for(Tweet tweet: tweets){
            mTweetsContainer.addView(bindView(tweet));
        }
    }

    private ConstraintLayout bindView(Tweet tweet){
        ConstraintLayout view;
        view = (ConstraintLayout) mInflater.inflate(R.layout.view_tweet, null, false);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(tweet.getSenderName());

        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(tweet.getContent());

        LinearLayout commentsContainer = (LinearLayout) view.findViewById(R.id.comments);

        ArrayList<Comment> comments = tweet.getComments();

        if(comments != null &&  comments.size() > 0) {
            for (Comment comment : tweet.getComments()) {
                TextView commentView = new TextView(mContext);
                StringBuffer strBuf = new StringBuffer(comment.getSenderName());
                strBuf.append(" : ");
                strBuf.append(comment.getContent());

                commentView.setText(strBuf.toString());
                commentsContainer.addView(commentView);
            }
        }
        return view;
    }
}
