package com.example.shi.tweets.usecase;

/**
 * Created by shi on 2018/4/14.
 */

public abstract class UseCase<R extends UseCase.RequestValues, P extends UseCase.ResponseValue> {

    private R mRequestValues;
    private UseCaseCallback<P> mUseCaseCallback;


    public void setRequestValues(R requestValues) {

        mRequestValues = requestValues;

    }



    public R getRequestValues() {

        return mRequestValues;

    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    void run() {
        executeUseCase(mRequestValues);
    }


    protected abstract void executeUseCase(R requestValues);


    public interface RequestValues {

    }


    public interface ResponseValue {

    }

    public interface UseCaseCallback<P> {

        void onSuccess(P response);

        void onError(String errorMsg);

    }
}
