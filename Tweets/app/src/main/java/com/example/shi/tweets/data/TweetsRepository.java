package com.example.shi.tweets.data;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.shi.tweets.executor.AppExecutor;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by shi on 2018/4/14.
 */

public class TweetsRepository implements DateSource {

    private static TweetsRepository INSTANCE;
    private DateSource mRemoteDateSource;
    private LocalDateSource mLocalDateSource;

    private TweetsRepository(){

        mRemoteDateSource = new RemoteDataSource();
        mLocalDateSource = new LocalDateSource();
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

    @Override
    public void loadImage(@NonNull String url, @NonNull final LoadImageCallBack callBack) {
        if(isFileExist(url)){
            mLocalDateSource.loadImage(url, callBack);
        }else{
            mRemoteDateSource.loadImage(url, new LoadImageCallBack() {
                @Override
                public void onLoaded(ImageResponse response) {
                    final Bitmap bitmap = response.getImage();
                    final String url = response.getUrl();
                    callBack.onLoaded(response);
                    AppExecutor.getInstance().getmIOExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            sameImage(url, bitmap);
                        }
                    });
                }

                @Override
                public void onFail(String reason) {
                    callBack.onFail(reason);
                }
            });
        }

    }

    private boolean isFileExist(String url){
        String strFile = getPath() + getFilenameForKey(url);
        try {
            File f=new File(strFile);
            if(!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void sameImage(String url, Bitmap bitmap){
        String dir = getPath();
        String fileName = getFilenameForKey(url);
        try {
            File file = new File(dir + fileName + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getPath(){
        String dir = Environment.getDataDirectory().getAbsolutePath() + "/tweets/image/";
        return dir;
    }

    private String getFilenameForKey(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());
        return localFilename;
    }
}
