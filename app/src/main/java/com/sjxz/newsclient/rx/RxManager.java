package com.sjxz.newsclient.rx;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:53
 */
public class RxManager {
    private RxManager() {

    }

    public static RxManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RxManager INSTANCE = new RxManager();
    }

    public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



}
