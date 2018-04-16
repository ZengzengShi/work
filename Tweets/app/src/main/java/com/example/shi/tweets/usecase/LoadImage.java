package com.example.shi.tweets.usecase;

import android.graphics.Bitmap;

import com.example.shi.tweets.data.DateSource;
import com.example.shi.tweets.data.TweetsRepository;


/**
 * Created by shi on 2018/4/16.
 */

public class LoadImage extends UseCase<LoadImage.LoadImageRequest, LoadImage.LoadImageResponse> {

    private TweetsRepository mRepository;

    public LoadImage(TweetsRepository repository){
        mRepository = repository;
    }

    @Override
    protected void executeUseCase(LoadImageRequest requestValues) {
        mRepository.loadImage(requestValues.getUrl(), new DateSource.LoadImageCallBack() {
            @Override
            public void onLoaded(DateSource.ImageResponse response) {

                getUseCaseCallback().onSuccess(
                        new LoadImageResponse(response.getUrl(), response.getImage()));
            }

            @Override
            public void onFail(String reason) {
                getUseCaseCallback().onError(reason);

            }
        });
    }

    public static final class LoadImageRequest implements UseCase.RequestValues{
        private String mUrl;

        public String getUrl(){
            return mUrl;
        }

        public LoadImageRequest(String url){
            mUrl = url;
        }

    }

    public static final class LoadImageResponse implements UseCase.ResponseValue{

        private String mUrl;
        private Bitmap mBitmap;

        public LoadImageResponse(String url, Bitmap bitmap){
            mUrl = url;
            mBitmap = bitmap;
        }


        public Bitmap getBitmap(){
            return mBitmap;
        }

    }
}
