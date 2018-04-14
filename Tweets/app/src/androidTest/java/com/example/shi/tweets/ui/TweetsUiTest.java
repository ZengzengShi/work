package com.example.shi.tweets.ui;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.shi.tweets.Injection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Created by shi on 2018/4/14.
 */

@RunWith(AndroidJUnit4.class)
public class TweetsUiTest {

    @Rule
     public ActivityTestRule<TweetsActivity> activityTestRule = new ActivityTestRule<TweetsActivity>(TweetsActivity.class){

        @Override
        protected void beforeActivityLaunched() {

            super.beforeActivityLaunched();
        }
    };

    @Test
    public void testLoadAll(){
        TweetsActivity activity = activityTestRule.getActivity();
        TweetsPresenter presenter = activity.getmPresenter();
        TweetsPresenter spy = Mockito.spy(presenter);
        //verify(spy).loadAllTweets();
    }
}
