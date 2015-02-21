package com.zsiegel.core.presenter;

import com.zsiegel.core.view.IView;

import rx.Observable;
import rx.Subscriber;

/**
 * A interface for our presenters
 *
 * @author zsiegel
 */
public interface IPresenter<T> {

    //This is the typical way I prefer - just un-subscribe in finish()
    public void start();
    
    //This helps demonstrate how you could hook into the lifecycle
    public Observable<T> getObservable();
    public Subscriber<T> getSubscriber();
    
    public void finish();
    public void setView(IView<T> view);
}
