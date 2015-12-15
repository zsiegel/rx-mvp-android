package com.zsiegel.sample.rxmvpandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zsiegel.core.model.User;
import com.zsiegel.core.presenter.UserPresenter;
import com.zsiegel.core.service.UserService;
import com.zsiegel.core.util.IScheduler;
import com.zsiegel.core.view.IView;
import com.zsiegel.sample.rxmvpandroid.R;
import com.zsiegel.sample.rxmvpandroid.util.AppScheduler;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zsiegel()
 */
public class UsersActivity extends BaseActivity implements IView<List<User>> {

    //We would inject these via Dagger in a real app
    private UserService userService;
    private IScheduler scheduler;

    @Bind(R.id.list)
    ListView listView;

    @Bind(R.id.loading)
    ProgressBar loadingView;

    private ArrayAdapter<User> userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merge_list_layout);

        //Create or inject services
        scheduler = new AppScheduler();
        userService = new UserService();

        //Create or inject your presenter
        presenter = new UserPresenter(userService, scheduler);
        presenter.setView(this);

        //Inject views
        ButterKnife.bind(this);

        //Setup your adapter
        userAdapter = new ArrayAdapter<>(this, R.layout.list_item);
        listView.setAdapter(userAdapter);
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
