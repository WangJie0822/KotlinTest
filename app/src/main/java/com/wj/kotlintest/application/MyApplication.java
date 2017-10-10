package com.wj.kotlintest.application;

import android.app.Activity;
import android.app.Application;

import com.wj.kotlintest.dagger.sub.application.DaggerApplicationSub;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * @author 王杰
 */
public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationSub
                .create()
                .inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
