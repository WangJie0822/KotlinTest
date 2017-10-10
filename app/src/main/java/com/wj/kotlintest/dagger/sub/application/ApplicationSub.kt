package com.wj.kotlintest.dagger.sub.application

import com.wj.kotlintest.application.MyApplication
import com.wj.kotlintest.dagger.module.ActivityModule
import dagger.Component
import javax.inject.Singleton

/**
 * Application Dagger2 组件
 *
 * @author 王杰
 */
@Singleton
@Component(modules = arrayOf(ActivityModule::class))
interface ApplicationSub {

    fun inject(app: MyApplication)
}
