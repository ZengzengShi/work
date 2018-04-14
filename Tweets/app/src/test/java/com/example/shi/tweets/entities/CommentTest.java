package com.example.shi.tweets.entities;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by shi on 2018/4/13.
 */


public class CommentTest {
    @Test
    public void testConstructor(){
        Comment comment = new Comment();
        assertNotNull(comment);
    }

}
