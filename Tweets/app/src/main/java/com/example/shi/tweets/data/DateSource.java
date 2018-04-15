package com.example.shi.tweets.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/14.
 */

public interface DateSource {

    interface LoadCallBack{
        void onLoaded(ArrayList<Tweet> tweets);
        void onFail(String reason);
    }

    interface LoadImageCallBack{
        void onLoaded(ImageResponse response);
        void onFail(String reason);
    }

    class ImageResponse{
        private Bitmap mBitmap;
        private String mUrl;

        public ImageResponse(Bitmap bitmap, String url){
            mBitmap = bitmap;
            mUrl = url;
        }

        public Bitmap getImage(){
            return mBitmap;
        }

        public String getUrl(){
            return mUrl;
        }
    }

    void getTweets(@NonNull String user, @NonNull LoadCallBack callBack);

    void loadImage(@NonNull String url, @NonNull LoadImageCallBack callBack);

    public void destroy();
}
