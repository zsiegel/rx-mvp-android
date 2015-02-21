package com.zsiegel.sample.rxmvpandroid.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zsiegel.core.model.User;
import com.zsiegel.core.presenter.IPresenter;
import com.zsiegel.core.presenter.UserPresenter;
import com.zsiegel.core.service.UserService;
import com.zsiegel.core.util.IScheduler;
import com.zsiegel.core.view.IView;
import com.zsiegel.sample.rxmvpandroid.R;
import com.zsiegel.sample.rxmvpandroid.util.AppScheduler;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.app.RxActivity;
import rx.android.lifecycle.LifecycleObservable;
import rx.observers.Subscribers;

/**
 * @author zsiegel
 */
public class UsersLifecycleActivity extends RxActivity implements IView<List<User>> {

    private static final String TAG = UsersLifecycleActivity.class.getSimpleName();

    //We would inject these via Dagger in a real app
    private UserService userService;
    private IScheduler scheduler;

    @InjectView(R.id.list)
    ListView listView;

    @InjectView(R.id.loading)
    ProgressBar loadingView;

    private Subscription subscription = Subscribers.empty();
    private ArrayAdapter<User> userAdapter;
    private IPresenter<List<User>> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merge_list_layout);

        //Create or inject services
        scheduler = new AppScheduler();
        userService = new UserService();

        //Inject views
        ButterKnife.inject(this);

        //Setup your adapter
        userAdapter = new ArrayAdapter<>(this, R.layout.list_item);
        listView.setAdapter(userAdapter);

        //Create or inject your presenter
        presenter = new UserPresenter(userService, scheduler);
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription = LifecycleObservable
                .bindActivityLifecycle(lifecycle(), presenter.getObservable())
                .subscribe(presenter.getSubscriber());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "");
        Log.i(TAG, "onStop() Subscription is un-subscribed " + subscription.isUnsubscribed());
    }

    @Override
    public void setLoading(boolean isLoading) {
        Log.i(TAG, "setLoading()");
        loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setModel(List<User> object) {
        Log.i(TAG, "setModel()");
        userAdapter.clear();
        userAdapter.addAll(object);
    }

    @Override
    public void error(Throwable t) {

    }
}
