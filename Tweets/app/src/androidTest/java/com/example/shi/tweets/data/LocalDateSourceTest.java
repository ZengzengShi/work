package com.example.shi.tweets.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Created by shi on 2018/4/16.
 */

@RunWith(AndroidJUnit4.class)
public class LocalDateSourceTest {

    @Test
    public void testFileOperate(){
        LocalDateSource localDateSource = new LocalDateSource();
        byte[] data = new byte[]{22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,
                22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,
                22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,
                22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,
                22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,
                22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,
                22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,
                22, 34, 2, 88, 10, 49, 43, 5, 0, 0, 0,};
        Bitmap testBitmap = BitmapFactory.decodeByteArray(data, 0,data.length, null );

        final String url = "http://www.shi.com";

        localDateSource.saveImage(url, testBitmap);

        try {
            wait(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(localDateSource.isFileExist(url));

        localDateSource.loadImage(url, new DateSource.LoadImageCallBack() {
            @Override
            public void onLoaded(DateSource.ImageResponse response) {
                assertTrue(response.getUrl()!= null);
                assertEquals(url, response.getUrl());
            }

            @Override
            public void onFail(String reason) {

            }
        });
    }
}
