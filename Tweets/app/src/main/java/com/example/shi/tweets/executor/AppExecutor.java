package com.example.shi.tweets.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by shi on 2018/4/14.
 */

public class AppExecutor {

    static final int THREAD_COUNT = 3;

    private Executor mNetworkExcutor;
    private Executor mIOExecutor;
    private Executor mMainExecutor;

    private static AppExecutor mAppExecutor;

    private AppExecutor(Executor networkExecutor, Executor IOExecutor, Executor mainExecutor) {
        mNetworkExcutor = networkExecutor;
        mIOExecutor = IOExecutor;
        mMainExecutor = mainExecutor;

    }

    public static AppExecutor getInstance(){
        if(mAppExecutor == null) {
            mAppExecutor =  new AppExecutor(Executors.newFixedThreadPool(THREAD_COUNT),
                    Executors.newSingleThreadExecutor(), new MainThreadExecutor());
        }
        return mAppExecutor;
    }

    public Executor getNetworkExcutor (){
        return mNetworkExcutor;
    }

    public Executor getmIOExecutor(){
        return mIOExecutor;
    }

    public Executor getMainExecutor(){
        return mMainExecutor;
    }

    public void destroy(){
        mAppExecutor = null;
    }


    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());



        @Override

        public void execute(@NonNull Runnable command) {

            mainThreadHandler.post(command);

        }
    }
}
