package com.example.shi.tweets.entities;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by shi on 2018/4/13.
 */

public class TweetTest {
    @Test
    public void testConstructor(){
        Tweet tweet = new Tweet("content", null, null, null);
        assertNotNull(tweet);
    }

    @Test
    public void testGetName(){
        Tweet tweet = new Tweet("content", null, null, null);

        assertEquals(null, tweet.getSenderName());
    }
}
