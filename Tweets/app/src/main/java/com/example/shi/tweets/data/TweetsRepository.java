package com.example.shi.tweets.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

/**
 * Created by shi on 2018/4/14.
 */

public class TweetsRepository implements DateSource {

    private static TweetsRepository INSTANCE;
    private DateSource mRemoteDateSource;

    private TweetsRepository(){

        mRemoteDateSource = new RemoteDataSource();
    }

    public static TweetsRepository getInstance(){
        if (INSTANCE == null){
            INSTANCE = new TweetsRepository();
        }

        return INSTANCE;
    }

    @VisibleForTesting
    public TweetsRepository(DateSource remoteDataSource){
        mRemoteDateSource = remoteDataSource;
    }

    @Override
    public void destroy(){
        INSTANCE = null;
        mRemoteDateSource.destroy();
    }

    @Override
    public void getTweets(@NonNull String user, @NonNull LoadCallBack callBack) {

        mRemoteDateSource.getTweets(user, callBack);
    }
}
