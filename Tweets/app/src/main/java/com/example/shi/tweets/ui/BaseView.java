package com.example.shi.tweets.ui;

/**
 * Created by shi on 2018/4/14.
 */

public interface BaseView<T extends BasePresenter> {
    public void setPresence(T presence);
}
