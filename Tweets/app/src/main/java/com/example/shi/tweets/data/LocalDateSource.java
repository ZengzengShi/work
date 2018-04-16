package com.example.shi.tweets.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.shi.tweets.executor.AppExecutor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executor;

/**
 * Created by shi on 2018/4/15.
 */

public class LocalDateSource implements DateSource {

    private static final String TAG = LocalDateSource.class.getName();

    AppExecutor mAppExecutor;

    public LocalDateSource(){
        mAppExecutor = AppExecutor.getInstance();
    }


    @Override
    public void getTweets(@NonNull String user, @NonNull LoadCallBack callBack) {

    }

    @Override
    public void loadImage(@NonNull final String url, @NonNull final LoadImageCallBack callBack) {
        final Executor mainExecutor = mAppExecutor.getMainExecutor();
        mAppExecutor.getmIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap image = loadImage(url);

                mainExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(image != null) {
                            callBack.onLoaded(new ImageResponse(image, url));
                        }else{
                            callBack.onFail("load image file");
                        }
                    }
                });

            }
        });

    }

    @Override
    public void destroy() {

    }

    public boolean isFileExist(String url){
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

    public void saveImage(final String url, final Bitmap bitmap){

        AppExecutor.getInstance().getmIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
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
        });

    }

    public Bitmap loadImage(String url){
        String dir = getPath();
        String fileName = getFilenameForKey(url);
        Bitmap image;

        image = BitmapFactory.decodeFile(dir + fileName  + ".jpg", null);
        return image;
    }

    private String getPath(){
        String dir = Environment.getDataDirectory().getAbsolutePath() + "/tweets/image/";
        Log.d(TAG, "file path: " + dir);
        return dir;
    }

    private String getFilenameForKey(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());
        Log.d(TAG, "file name: " + localFilename);
        return localFilename;
    }
}
