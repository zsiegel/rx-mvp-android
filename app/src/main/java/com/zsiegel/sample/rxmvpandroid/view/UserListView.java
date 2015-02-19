package com.zsiegel.sample.rxmvpandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
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

/**
 * @author zsiegel
 */
public class UserListView extends FrameLayout implements IView<List<User>> {

    //We would inject these via Dagger in a real app
    private UserService userService;
    private IScheduler scheduler;

    private IPresenter<List<User>> usersPresenter;

    @InjectView(R.id.list)
    ListView listView;

    @InjectView(R.id.loading)
    ProgressBar loadingView;

    private ArrayAdapter<User> userAdapter;

    public UserListView(Context context) {
        this(context, null);
    }

    public UserListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    private void init(Context context) {
        View v = inflate(context, R.layout.merge_list_layout, this);
        ButterKnife.inject(this, v);

        //Create or inject services
        scheduler = new AppScheduler();
        userService = new UserService();

        //Create or inject your presenter
        usersPresenter = new UserPresenter(userService, scheduler);
        usersPresenter.setView(this);

        //Setup your adapter
        userAdapter = new ArrayAdapter<>(context, R.layout.list_item);
        listView.setAdapter(userAdapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        usersPresenter.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        usersPresenter.finish();
    }

    @Override
    public void setLoading(boolean isLoading) {
        loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setModel(List<User> users) {
        userAdapter.clear();
        userAdapter.addAll(users);
    }

    @Override
    public void error(Throwable t) {
        //Handle error here
    }
}
