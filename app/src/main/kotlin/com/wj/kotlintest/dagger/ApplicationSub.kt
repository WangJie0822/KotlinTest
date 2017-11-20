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
        /* Activity 注入组件*/
        ActivityModule::class,
        /** v4 包 Fragment 注入组件 */
        SupportFragmentModule::class,
        /* 网络请求注入组件 */
        NetModule::class,
        /* Android 支持注入组件 */
        AndroidSupportInjectionModule::class))
interface ApplicationSub : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}
