package com.example.shi.tweets.data;

import android.support.test.runner.AndroidJUnit4;

import com.example.shi.tweets.entities.Tweet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by shi on 2018/4/14.
 */
@RunWith(AndroidJUnit4.class)
public class TweetsRepositoryTest {

    private TweetsRepository testRepository;
    RemoteDataSource mockDateSource;

    @Before
    public void setUp(){
        mockDateSource = Mockito.mock(RemoteDataSource.class);
        testRepository = new TweetsRepository(mockDateSource);
    }

    @After
    public void clearUp(){
        testRepository.destroy();
    }

    @Test
    public void testGetTweets(){

        DateSource.LoadCallBack callBack = new DateSource.LoadCallBack() {
            @Override
            public void onLoaded(ArrayList<Tweet> tweets) {

            }

            @Override
            public void onFail(String reason) {

            }
        };
        testRepository.getTweets("test", callBack);

        verify(mockDateSource, times(1))
                .getTweets("test", callBack);
    }
}
