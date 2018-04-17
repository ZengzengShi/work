package com.example.shi.tweets.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shi.tweets.R;
import com.example.shi.tweets.entities.Comment;
import com.example.shi.tweets.entities.ImageUrl;
import com.example.shi.tweets.entities.Tweet;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TweetsActivityFragment extends Fragment implements UiContract.ItweetsView{

    private final String TAG = TweetsActivityFragment.class.getName();

    private UiContract.ItweetsPresenter mPresenter;
    private TextView mEmptyView;
    private SmartRefreshLayout mSwipeView;
    private ListView mTweetsContainer;
    private Context mContext;

    public TweetsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tweets, container, false);
        mEmptyView = (TextView) rootView.findViewById(R.id.empty_view);
        mTweetsContainer = (ListView) rootView.findViewById(R.id.tweet_container);

        mSwipeView = (SmartRefreshLayout) rootView.findViewById(R.id.swipe_view);
        mSwipeView.setRefreshHeader(new MaterialHeader(mContext).setShowBezierWave(true));
        mSwipeView.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        mSwipeView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.d(TAG, "on refresh! ");
                mPresenter.loadAllTweets();
                mSwipeView.finishRefresh(2000);
            }
        });

        mSwipeView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.d(TAG, "on load more! ");
                mPresenter.loadNextScreen();
                mSwipeView.finishLoadmore(2000);
            }
        });

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
        Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT);
    }

    void showTweets(ArrayList<Tweet> tweets){

        if (mTweetsContainer.getAdapter() == null){
            mTweetsContainer.setAdapter(new TweetsListAdapter(mContext,mPresenter, tweets));
        }else{
            TweetsListAdapter adpter = (TweetsListAdapter) mTweetsContainer.getAdapter();
            adpter.setDate(tweets);
            adpter.notifyDataSetChanged();
        }

    }

}
