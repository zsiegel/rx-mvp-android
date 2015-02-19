package com.zsiegel.sample.rxmvpandroid.activity;

import android.app.Activity;

import com.zsiegel.core.presenter.IPresenter;

/**
 * A base activity that will finish the bound presenter
 * <p/>
 * Note : the {@link IPresenter} should be un-subscribing in {@link IPresenter#finish()}
 *
 * @author zsiegel()
 */
public class BaseActivity extends Activity {

    IPresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.finish();
    }
}
