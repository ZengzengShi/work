package com.example.shi.tweets.data;

import com.example.shi.tweets.entities.Tweet;
import com.example.shi.tweets.executor.AppExecutor;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;
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


    String API_BASE_URL = "http://thoughtworks-ios.herokuapp.com/";
    private AppExecutor mAppExecutor;
    private TweetsClient mTweetsClient;

    public RemoteDataSource(){
        mAppExecutor = AppExecutor.getInstance();
        initialTweetsClient();
    }

    private void initialTweetsClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder() .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder
                .client( httpClient.build() )
                .build();

        mTweetsClient = retrofit.create( TweetsClient.class );
    }

    @Override
    public void destroy(){
        mAppExecutor.destroy();
    }

    @Override
    public void getTweets(final String user, final LoadCallBack callBack){

        mAppExecutor.getNetworkExcutor().execute(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Tweet>> call = mTweetsClient.getTweets(user);
                final Executor mainExecutor = mAppExecutor.getMainExecutor();
                call.enqueue(new Callback<ArrayList<Tweet>>() {

                    @Override
                    public void onResponse(Call<ArrayList<Tweet>> call, final Response<ArrayList<Tweet>> response){
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
                            }
                        });
                    }
                });
            }
        });

    }
}
