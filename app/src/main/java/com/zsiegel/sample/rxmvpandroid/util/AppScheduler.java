package com.zsiegel.sample.rxmvpandroid.util;

import com.zsiegel.core.util.IScheduler;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zsiegel
 */
public class AppScheduler implements IScheduler {

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler backgroundThread() {
        return Schedulers.io();
    }
}
