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
import com.zsiegel.sample.rxmvpandroid.R;
import com.zsiegel.sample.rxmvpandroid.util.AppScheduler;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.RxActivity;
import rx.android.lifecycle.LifecycleObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * @author zsiegel
 */
public class UsersLifecycleActivity extends RxActivity {

    private static final String TAG = UsersLifecycleActivity.class.getSimpleName();

    //We would inject these via Dagger in a real app
    private UserService userService;
    private IScheduler scheduler;

    @InjectView(R.id.list)
    ListView listView;

    @InjectView(R.id.loading)
    ProgressBar loadingView;

    private ArrayAdapter<User> userAdapter;
    private Subscription subscription = Subscriptions.empty();
    private IPresenter<List<User>> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merge_list_layout);

        //Create or inject services
        scheduler = new AppScheduler();
        userService = new UserService();

        //Create or inject your presenter
        presenter = new UserPresenter(userService, scheduler);

        //NOTE we do NOT set the IView here since we subscribe in onStart()

        //Inject views
        ButterKnife.inject(this);

        //Setup your adapter
        userAdapter = new ArrayAdapter<>(this, R.layout.list_item);
        listView.setAdapter(userAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscription = LifecycleObservable.bindActivityLifecycle(lifecycle(), presenter.startForLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.i(TAG, "onStart");
                        userAdapter.clear();
                        loadingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                        loadingView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error", e);
                    }

                    @Override
                    public void onNext(List<User> users) {
                        Log.i(TAG, "onNext");
                        userAdapter.clear();
                        userAdapter.addAll(users);
                    }
                });
    }
}
