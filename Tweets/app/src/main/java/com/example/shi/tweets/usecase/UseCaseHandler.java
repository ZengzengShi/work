package com.example.shi.tweets.usecase;

/**
 * Created by shi on 2018/4/14.
 */

public class UseCaseHandler {

    private static UseCaseHandler INSTANCE;
    private final UseCaseScheduler mUseCaseScheduler;


    public UseCaseHandler (UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements
            UseCase.UseCaseCallback<V> {

        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback,
                                 UseCaseHandler useCaseHandler) {

            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }


        @Override
        public void onSuccess(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError(String errorMsg) {
            mUseCaseHandler.notifyError(mCallback);
        }

    }




    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(

            final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {

        useCase.setRequestValues(values);

        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });

    }


    public <V extends UseCase.ResponseValue> void notifyResponse(
            final V response,
            final UseCase.UseCaseCallback<V> useCaseCallback) {

        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }



    private <V extends UseCase.ResponseValue> void notifyError(
            final UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.onError(useCaseCallback);

    }

    public static UseCaseHandler getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler(new UseCaseScheduler());
        }
        return INSTANCE;
    }

    public static void destroy(){
        if(INSTANCE != null) {
            INSTANCE = null;
        }
    }

}
