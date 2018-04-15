package com.example.shi.tweets.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shi on 2018/4/15.
 */

public class FilterFactory {

    public static final int VALIDE_TWEETS_FILTER = 1;

    public static IFilter getFilter(int filterType){

        if(filterType == VALIDE_TWEETS_FILTER){
            return new ValideTweetsFilter();
        }
        return null;
    }

    public interface IFilter<T extends Object>{
       public ArrayList<T> filter(ArrayList<T> input);
    }
}
