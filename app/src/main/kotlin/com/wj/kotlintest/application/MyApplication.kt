package com.wj.kotlintest.application

import android.annotation.SuppressLint
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.wj.kotlintest.BuildConfig
import com.wj.kotlintest.dagger.DaggerApplicationSub
import com.wj.kotlintest.databinding.DataBindingAdapter
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Application 类，集成 Dagger2 继承[DaggerApplication]
 */
class MyApplication : DaggerApplication() {

    companion object {
        /** MyApplication 实例对象 */
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: MyApplication
    }

    override fun onCreate() {
        super.onCreate()

        // 保存实例对象
        INSTANCE = this

        // 初始化 Logger
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        })

        // 配置 DataBindingAdapter
        DataBindingUtil.setDefaultComponent(object : DataBindingComponent {
            override fun getDataBindingAdapter() = DataBindingAdapter
        })

    }

    override fun applicationInjector()
            : AndroidInjector<MyApplication> = DaggerApplicationSub.builder().create(this)

}