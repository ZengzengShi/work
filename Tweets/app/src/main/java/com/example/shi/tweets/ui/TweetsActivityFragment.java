package com.example.shi.tweets.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shi.tweets.R;
import com.example.shi.tweets.entities.Comment;
import com.example.shi.tweets.entities.ImageUrl;
import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TweetsActivityFragment extends Fragment implements UiContract.ItweetsView{

    private UiContract.ItweetsPresenter mPresenter;

    private TextView mEmptyView;

    private SwipeRefreshLayout mSwipeView;
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
        mSwipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_view);
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadAllTweets();
                mSwipeView.setRefreshing(false);
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

    }

    void showTweets(ArrayList<Tweet> tweets){
        mTweetsContainer.removeAllViews();
        for(Tweet tweet: tweets){
            mTweetsContainer.addView(bindView(tweet));
        }
    }

    private ConstraintLayout bindView(Tweet tweet){
        ConstraintLayout view;
        view = (ConstraintLayout) mInflater.inflate(R.layout.view_tweet, null, false);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(tweet.getSenderName());

        if(tweet.getImagesUrl() != null) {
            GridView imagesView = (GridView) view.findViewById(R.id.images);
            imagesView.setAdapter(new ImageAdapter(getContext(), tweet.getImagesUrl()));
        }

        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(tweet.getContent());

        final String url = tweet.getSenderAvatar();
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.avator);
        mPresenter.loadImage(url, new UiContract.UiLoadImageCallBack() {
            @Override
            public void onLoaded(Bitmap bitmap) {
                avatarImage.setImageBitmap(bitmap);

            }
        });

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

    class ImageAdapter extends BaseAdapter{

        private Context mContext;
        private ArrayList<ImageUrl> mUrls;

        public ImageAdapter(Context context, ArrayList<ImageUrl> urls){
            super();
            mContext = context;
            mUrls = urls;
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            final ImageView image = new ImageView(mContext);
            image.setLayoutParams(new GridView.LayoutParams(300, 300));
            mPresenter.loadImage(mUrls.get(position).getStrUrl(),
                    new UiContract.UiLoadImageCallBack() {
                @Override
                public void onLoaded(Bitmap bitmap) {
                    image.setImageBitmap(bitmap);
                }
            });

            return image;

        }
    }
}
