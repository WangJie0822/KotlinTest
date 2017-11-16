package com.wj.kotlintest.dagger

import com.wj.kotlintest.application.MyApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Application Dagger2 组件
 */
@Singleton
@Component(modules = arrayOf(
        ActivityModule::class,
        SupportFragmentModule::class,
        NetModule::class,
        AndroidSupportInjectionModule::class))
interface ApplicationSub : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}
