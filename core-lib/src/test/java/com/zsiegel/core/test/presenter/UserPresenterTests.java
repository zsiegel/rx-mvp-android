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
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;

/**
 * @author zsiegel
 */
public class UserPresenterTests {

    @Mock
    IView<List<User>> mockView;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPresenterWithMockService() {

        UserService userService = mock(UserService.class);

        UserPresenter userPresenter = new UserPresenter(userService, new TestScheduler());

        when(userService.getUsers()).thenReturn(Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                subscriber.onNext(new ArrayList<User>());
                subscriber.onCompleted();
            }
        }));

        userPresenter.setView(mockView);
        userPresenter.start();

        verify(mockView, times(1)).setLoading(true);
        verify(mockView, times(1)).setLoading(false);
        verify(mockView, times(1)).setModel(anyListOf(User.class));
        verify(mockView, never()).error(any(Throwable.class));
    }

    @Test
    public void testPresenterWithServiceImpl() {

        UserPresenter userPresenter = new UserPresenter(new UserService(), new TestScheduler());

        userPresenter.setView(mockView);
        userPresenter.start();

        verify(mockView, times(1)).setLoading(true);
        verify(mockView, times(1)).setLoading(false);
        verify(mockView, times(1)).setModel(anyListOf(User.class));
        verify(mockView, never()).error(any(Throwable.class));
    }
}
