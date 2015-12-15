package com.zsiegel.core.presenter;

import com.zsiegel.core.view.IView;

/**
 * A interface for our presenters
 *
 * @author zsiegel
 */
public interface IPresenter<T> {

    void start();

    void finish();

    void setView(IView<T> view);
}
