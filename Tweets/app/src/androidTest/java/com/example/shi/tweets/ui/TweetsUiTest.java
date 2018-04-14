package com.example.shi.tweets.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Created by shi on 2018/4/14.
 */

@RunWith(AndroidJUnit4.class)
public class TweetsUiTest {

    @Rule
     ActivityTestRule<TweetsActivity> activityTestRule = new ActivityTestRule<TweetsActivity>(TweetsActivity.class){

        @Override
        protected void beforeActivityLaunched() {

            super.beforeActivityLaunched();

            // Doing this in @Before generates a race condition.

        }
    };
}
