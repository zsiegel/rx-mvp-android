package com.zsiegel.core.util;

import rx.Scheduler;

/**
 * We use a scheduler to make testing our presenters easier
 * @author zsiegel
 */
public interface IScheduler {

    public Scheduler mainThread();

    public Scheduler backgroundThread();
}
