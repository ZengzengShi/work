package com.example.shi.tweets.entities;

import com.example.shi.tweets.data.entities.Tweet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by shi on 2018/4/13.
 */

public class TweetTest {
    @Test
    public void testConstructor(){
        Tweet tweet = new Tweet();
        assertNotNull(tweet);
    }
}
