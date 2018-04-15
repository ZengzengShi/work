package com.example.shi.tweets.filter;

import com.example.shi.tweets.entities.ImageUrl;
import com.example.shi.tweets.entities.Tweet;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/15.
 */

public class ValidedTweetsFilter {

    @Test
    public void testFilter(){
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(new Tweet("first",null, null, null));

        ArrayList<ImageUrl> images = new ArrayList<>();
        images.add(new ImageUrl("http://www.test.com"));

        tweets.add(new Tweet(null, images, null, null));
        tweets.add(new Tweet(null, null, null, null));

        ArrayList<ImageUrl> images1 = new ArrayList<>();
        tweets.add(new Tweet(null, images1, null, null));
        tweets.add(new Tweet("", images1, null, null));

        ArrayList<Tweet> filtedList = FilterFactory.getFilter(FilterFactory.VALIDE_TWEETS_FILTER)
                .filter(tweets);

        assertEquals(2, filtedList.size());
        assertEquals("first", filtedList.get(0).getContent());
        assertEquals(images, filtedList.get(1).getImagesUrl());
    }
}
