package com.example.shi.tweets.entities;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by shi on 2018/4/13.
 */


public class CommentTest {
    @Test
    public void testConstructor(){
        Comment comment = new Comment("test",
                new Sender("name", "nick", "avatar"));
        assertNotNull(comment);
    }

    @Test
    public void testGetSenderName(){

        Comment comment = new Comment("test",
                new Sender("name", "nick", "avatar"));

        assertEquals("name", comment.getSenderName());

        Comment comment1 = new Comment("test",
                new Sender(null, "nick", "avatar"));

        assertEquals(null, comment1.getSenderName());
    }

}
