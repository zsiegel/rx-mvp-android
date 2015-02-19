package com.zsiegel.core.service;

import com.zsiegel.core.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;

/**
 * @author zsiegel
 */
public class UserService {

    public UserService() {
        super();
    }

    public Observable<List<User>> getUsers() {
        return Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {

                //Simulate some latency
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }

                List<User> users = new ArrayList<>();
                for (int idx = 0; idx < 50; idx++) {
                    User user = new User();
                    user.id = idx;
                    user.age = new Random().nextInt(100);
                    user.firstName = "Billy";
                    user.lastName = "Bob";
                    users.add(user);
                }
                subscriber.onNext(users);
                subscriber.onCompleted();
            }
        });
    }
}
