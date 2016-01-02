package com.zsiegel.sample.rxmvpandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.zsiegel.core.model.User;
import com.zsiegel.core.presenter.UserPresenter;
import com.zsiegel.core.service.UserService;
import com.zsiegel.core.util.IScheduler;
import com.zsiegel.core.view.IView;
import com.zsiegel.sample.rxmvpandroid.R;
import com.zsiegel.sample.rxmvpandroid.util.AppScheduler;

import java.util.List;

/**
 * @author zsiegel()
 */
public class UsersActivity extends Activity implements IView<List<User>> {

    //We would inject these via Dagger in a real app
    private UserService userService;
    private IScheduler scheduler;

    @Bind(R.id.list)
    ListView listView;

    @Bind(R.id.loading)
    ProgressBar loadingView;

    private UserPresenter presenter;
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

        presenter.start();
    }

    @Override
    public void setLoading(boolean isLoading) {
        loadingView.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.finish();
    }
}
