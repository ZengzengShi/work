package com.example.shi.tweets.ui;

import com.example.shi.tweets.Injection;
import com.example.shi.tweets.data.DateSource;
import com.example.shi.tweets.data.TweetsRepository;
import com.example.shi.tweets.usecase.GetTweets;
import com.example.shi.tweets.usecase.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by shi on 2018/4/14.
 */

public class TweetsPresenterTest {

    @Mock
    private GetTweets getTweets;

    @Mock
    private UiContract.ItweetsView view;

    @Mock
    private TweetsRepository repository;

    @Captor
    private ArgumentCaptor<DateSource.LoadCallBack> mLoadCallbackCaptor;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadAllTweets(){

        TweetsPresenter presenter = new TweetsPresenter(
                Injection.providerUseCaseHandler(),
                repository,new GetTweets(repository), view);

        presenter.loadAllTweets();

        verify(repository, times(1)).getTweets(
                anyString(), mLoadCallbackCaptor.capture());
    }
}
