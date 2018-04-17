package com.example.shi.tweets.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shi.tweets.R;
import com.example.shi.tweets.entities.Comment;
import com.example.shi.tweets.entities.ImageUrl;
import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/17.
 */

public class TweetsListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Tweet> mTweets;
    private UiContract.ItweetsPresenter mPresenter;
    private LayoutInflater mInflater;

    public TweetsListAdapter(Context context, UiContract.ItweetsPresenter presenter, ArrayList<Tweet> tweets){
        mContext = context;
        mPresenter = presenter;
        mTweets = tweets;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(mTweets == null){
            return 0;
        }
        return mTweets.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setDate(ArrayList<Tweet> tweets){
        mTweets = tweets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = mTweets.get(position);

        ConstraintLayout view;
        view = (ConstraintLayout) mInflater.inflate(R.layout.view_tweet, null, false);

        bindView(view, tweet);
        return view;
    }

    private void bindView(ConstraintLayout view, Tweet tweet){
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(tweet.getSenderName());

        if(tweet.getImagesUrl() != null) {
            GridView imagesView = (GridView) view.findViewById(R.id.images);
            imagesView.setAdapter(new ImageAdapter(mContext, tweet.getImagesUrl()));
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
