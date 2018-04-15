package com.example.shi.tweets.useCase;

import android.os.Looper;
//import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestRunner;
import android.test.InstrumentationTestCase;
import android.test.InstrumentationTestRunner;
import android.test.UiThreadTest;

import com.example.shi.tweets.usecase.UseCase;
import com.example.shi.tweets.usecase.UseCaseHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 * Created by shi on 2018/4/14.
 */

//@RunWith(AndroidJUnit4.class)
public class UseCaseHandlerTest extends InstrumentationTestCase {
    UseCaseHandler handler;

    @Before
    @UiThreadTest
    public void setUp(){

    }

    @After
    @UiThreadTest
    public void clearUp(){
        UseCaseHandler.destroy();
    }

    @UiThreadTest
    public void testUseCaseExecute(){
        handler = UseCaseHandler.getInstance();
        handler.execute(new TestUseCase(), new TestUseCase.TestRequest(5),
                new UseCase.UseCaseCallback<TestUseCase.TestResponse>() {

            @Override
            public void onSuccess(TestUseCase.TestResponse response) {
                assertEquals(6, response.getResponse());
                assertTrue(isMainThread());
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}
