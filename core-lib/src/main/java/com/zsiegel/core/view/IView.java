package com.zsiegel.core.view;

/**
 * @author zsiegel
 */
public interface IView<T> {

    public void setLoading(boolean isLoading);

    public void setModel(T object);

    public void error(Throwable t);
}
