package com.example.shi.tweets.filter;

import com.example.shi.tweets.entities.ImageUrl;
import com.example.shi.tweets.entities.Tweet;

import java.util.ArrayList;

/**
 * Created by shi on 2018/4/15.
 */

public class ValideTweetsFilter implements FilterFactory.IFilter <Tweet>{
    @Override
    public ArrayList<Tweet> filter(ArrayList<Tweet> input) {

        if(input == null){
            return null;
        }

        int index = 0;
        while(index < input.size()){
            if(isEmptyStr(input.get(index).getContent())
                    && isEmptyImages(input.get(index).getImagesUrl())){
                input.remove(index);
            }else{
                index++;
            }
        }
        return input;
    }

    private boolean isEmptyStr(String str){
        if(str == null){
            return true;
        }

        if(str.isEmpty()){
            return true;
        }

        return false;
    }

    private boolean isEmptyImages(ArrayList<ImageUrl> urls){
        if(urls == null){
            return true;
        }

        if(urls.size() == 0){
            return true;
        }

        boolean isEmpty = true;

        for(ImageUrl url : urls) {
            if (!isEmptyStr(url.getStrUrl())) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }
}
