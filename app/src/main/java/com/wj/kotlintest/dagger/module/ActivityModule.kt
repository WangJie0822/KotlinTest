package com.wj.kotlintest.dagger.module

import android.app.Activity

import com.wj.kotlintest.activity.MainActivity
import com.wj.kotlintest.dagger.sub.activity.MainActivitySub

import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Activity Dagger2 Module
 *
 * @author 王杰
 */
@Module(subcomponents = arrayOf(MainActivitySub::class))
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivity(builder: MainActivitySub.Builder): AndroidInjector.Factory<out Activity>

}
