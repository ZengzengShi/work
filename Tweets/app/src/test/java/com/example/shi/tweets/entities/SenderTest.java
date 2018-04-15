package com.example.shi.tweets.entities;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by shi on 2018/4/13.
 */

public class SenderTest {
    @Test
    public void testConstructor(){
        Sender sender = new Sender("name", "nick", "avatar");
        assertNotNull(sender);
    }
}
