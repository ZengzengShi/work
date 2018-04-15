package com.example.shi.tweets.usecase;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shi on 2018/4/14.
 */

public class UseCaseScheduler {


    public static final int POOL_SIZE = 2;
    public static final int MAX_POOL_SIZE = 4;
    public static final int TIMEOUT = 30;

    ThreadPoolExecutor mThreadPoolExecutor;
    private final Handler mHandler = new Handler();


    public UseCaseScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE));
    }



    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }


    public <V extends UseCase.ResponseValue> void notifyResponse(
            final V response,
            final UseCase.UseCaseCallback<V> useCaseCallback) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSuccess(response);
            }
        });
    }


    public <V extends UseCase.ResponseValue> void onError(

            final UseCase.UseCaseCallback<V> useCaseCallback) {

        mHandler.post(new Runnable() {

            @Override
            public void run() {
                useCaseCallback.onError("error");
            }
        });
    }
}
