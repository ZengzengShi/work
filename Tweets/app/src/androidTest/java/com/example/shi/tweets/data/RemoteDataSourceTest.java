package com.example.shi.tweets.data;

import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;

import com.example.shi.tweets.entities.Tweet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/14.
 */

@RunWith(AndroidJUnit4.class)
public class RemoteDataSourceTest {

    private RemoteDataSource dataSource;

    @Before
    public void setUp(){
        dataSource = new RemoteDataSource();
    }

    @After
    public void clearUp(){
        dataSource.destroy();
        dataSource = null;
    }

    @Test
    public void testGetTweets(){
        dataSource.getTweets("jsmith", new DateSource.LoadCallBack() {
            @Override
            public void onLoaded(ArrayList<Tweet> tweets) {
                assertNotNull(tweets);
                assertTrue(tweets.size() > 1);
                assertTrue(isMainThread());
            }

            @Override
            public void onFail(String reason) {
                fail(reason);
                assertTrue(isMainThread());
            }
        });

    };

    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
