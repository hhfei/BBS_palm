package com.example.Help;


import android.graphics.drawable.Drawable;

import com.example.util.Tools;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hh on 2017/8/10.
 */

public class RxJavaNetHelper {

    public static void doNetworkAndUpdate(final String address, final String param, Action1<String> ob) {


        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                String info = NetUtils.post(address,param);
                subscriber.onNext(info);
//                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(ob);


    }
}
