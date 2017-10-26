package com.wj.kotlintest.dagger.module

import com.wj.kotlintest.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity Dagger2 Module
 *
 * @author 王杰
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity
}
