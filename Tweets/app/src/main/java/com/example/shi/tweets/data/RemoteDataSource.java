package com.example.shi.tweets.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.shi.tweets.entities.Tweet;
import com.example.shi.tweets.executor.AppExecutor;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by shi on 2018/4/13.
 */

public class RemoteDataSource implements DateSource{


    private static final String TAG = RemoteDataSource.class.getName();

    String API_BASE_URL = "http://thoughtworks-ios.herokuapp.com/";
    private AppExecutor mAppExecutor;
    private TweetsClient mTweetsClient;

    public RemoteDataSource(){
        mAppExecutor = AppExecutor.getInstance();
        initialTweetsClient();
    }

    private void initialTweetsClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        GsonConverterFactory factory = GsonConverterFactory.create();
        Retrofit.Builder builder = new Retrofit.Builder() .baseUrl(API_BASE_URL)
                .addConverterFactory(factory);

        Retrofit retrofit = builder
                .client(httpClient.build())
                .build();

        mTweetsClient = retrofit.create( TweetsClient.class );
    }

    @Override
    public void destroy(){
        mAppExecutor.destroy();
    }

    @Override
    public void getTweets(final String user, final LoadCallBack callBack){
        Log.e(TAG, "user = " + user);
        mAppExecutor.getNetworkExcutor().execute(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Tweet>> call = mTweetsClient.getTweets(user);
                Log.e(TAG, "request = " + call.request().toString());

                final Executor mainExecutor = mAppExecutor.getMainExecutor();
                call.enqueue(new Callback<ArrayList<Tweet>>() {

                    @Override
                    public void onResponse(Call<ArrayList<Tweet>> call, final Response<ArrayList<Tweet>> response){
                        outputTweets(response.body());
                        mainExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onLoaded(response.body());

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Tweet>> call, Throwable t) {
                        t.printStackTrace();
                        final String errorMsg = t.getMessage();
                        mainExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFail(errorMsg);
                                Log.d(TAG, "errorMsg: " + errorMsg);
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public void loadImage(@NonNull final String url, @NonNull final LoadImageCallBack callBack) {
        mAppExecutor.getNetworkExcutor().execute(new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> call = mTweetsClient.downLoadImage(url);
                Log.e(TAG, "request = " + call.request().toString());

                final Executor mainExecutor = mAppExecutor.getMainExecutor();
                call.enqueue(new Callback<ResponseBody>(){

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.body() == null){
                            mainExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onFail("null response!");
                                    return;
                                }
                            });
                        }

                        final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        final String url = call.request().url().toString();
                        Log.e(TAG, "url = " + url);
                        mainExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onLoaded(new ImageResponse(bitmap, url));
                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        final String errMsg = t.toString();
                        mainExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFail(errMsg);
                            }
                        });

                    }
                });
            }
        });
    }



    private void outputTweets(ArrayList<Tweet> tweets){
        if(tweets == null){
            Log.e(TAG, "null tweets");
            return;
        }
        for(Tweet tweet : tweets) {
            Log.d(TAG, "tweet: " + tweet.toString());
        }
    }
}
