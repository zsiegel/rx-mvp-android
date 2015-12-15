package com.zsiegel.core.view;

/**
 * @author zsiegel
 */
public interface IView<T> {

    void setLoading(boolean isLoading);

    void setModel(T object);

    void error(Throwable t);
}
