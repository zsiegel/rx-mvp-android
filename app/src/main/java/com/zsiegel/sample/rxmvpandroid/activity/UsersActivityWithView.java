package com.zsiegel.sample.rxmvpandroid.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zsiegel.sample.rxmvpandroid.R;

/**
 * This is a basic activity where the same presenter is used in a custom view
 * @author zsiegel()
 */
public class UsersActivityWithView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_view);
    }
}
