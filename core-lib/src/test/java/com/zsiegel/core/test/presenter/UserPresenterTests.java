package com.zsiegel.core.test.presenter;

import com.zsiegel.core.model.User;
import com.zsiegel.core.presenter.UserPresenter;
import com.zsiegel.core.service.UserService;
import com.zsiegel.core.test.TestScheduler;
import com.zsiegel.core.view.IView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author zsiegel
 */
public class UserPresenterTests {

    @Mock
    IView<List<User>> mockView;

    private UserPresenter userPresenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        userPresenter = new UserPresenter(new UserService(), new TestScheduler());
    }

    @Test
    public void testPresenter() {

        userPresenter.setView(mockView);
        userPresenter.start();

        verify(mockView, times(1)).setLoading(true);
        verify(mockView, times(1)).setLoading(false);
        verify(mockView, times(1)).setModel(anyListOf(User.class));
        verify(mockView, never()).error(any(Throwable.class));
    }
}
