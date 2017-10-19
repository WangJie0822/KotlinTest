package com.wj.kotlintest.application

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.wj.kotlintest.dagger.sub.application.DaggerApplicationSub
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 *
 *
 * @author 王杰
 */
class MyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: Application
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var supportFragInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        DaggerApplicationSub
                .builder()
                .build()
                .inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragInjector
    }
}