package com.zsiegel.core.presenter;

import com.zsiegel.core.view.IView;
import com.zsiegel.core.model.User;
import com.zsiegel.core.service.UserService;
import com.zsiegel.core.util.IScheduler;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * @author zsiegel
 */
public class UserPresenter implements IPresenter<List<User>> {

    private Subscription subscription = Subscriptions.empty();

    private UserService userService;
    private IScheduler scheduler;
    private IView<List<User>> view;

    public UserPresenter(UserService userService, IScheduler scheduler) {
        super();
        this.userService = userService;
        
        //By using this scheduler we can run the same presenter in various ways
        //When run in Android land we run the work asynchronously and push results to the UI thread
        //When run in the tests we run the work and publish results on the same thread
        this.scheduler = scheduler;
    }

    @Override
    public void setView(IView<List<User>> view) {
        this.view = view;
    }

    @Override
    public void start() {
        userService.getUsers()
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        view.setLoading(true);
                        view.setModel(new ArrayList<User>());
                    }

                    @Override
                    public void onCompleted() {
                        view.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setLoading(false);
                        view.error(e);
                    }

                    @Override
                    public void onNext(List<User> users) {
                        //NOTE We deviate slightly here from strict MVP
                        //We have opted here not to provide formatted data to the view
                        //We do this so that we can re-use presenters across multiple views
                        //The views themselves format the data as they see fit
                        view.setModel(users);
                    }
                });
    }

    @Override
    public Observable<List<User>> startForLifecycle() {
        return userService.getUsers();
    }

    @Override
    public void finish() {
        subscription.unsubscribe();
    }
}
