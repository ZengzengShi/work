package com.example.shi.tweets.useCase;

import com.example.shi.tweets.usecase.UseCase;

/**
 * Created by shi on 2018/4/14.
 */

public class TestUseCase extends UseCase<TestUseCase.TestRequest, TestUseCase.TestResponse> {

    @Override
    protected void executeUseCase(TestRequest requestValues) {

        getUseCaseCallback().onSuccess(new TestResponse(requestValues.getValue() + 1));
    }


    public static final class TestRequest implements UseCase.RequestValues{

        private int requestValue;

        public TestRequest(int value){
            requestValue = value;
        }

        public int getValue(){
            return requestValue;
        }
    }

    public static final class TestResponse implements UseCase.ResponseValue{

        private int responseVale;

        public TestResponse (int value){
            responseVale = value;
        }

        public int getResponse(){
            return responseVale;
        }
    }
}
