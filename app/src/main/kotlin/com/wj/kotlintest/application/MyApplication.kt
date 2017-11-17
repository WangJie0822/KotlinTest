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
 * Application 类
 */
class MyApplication : DaggerApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: MyApplication
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        })

        DataBindingUtil.setDefaultComponent(object : DataBindingComponent{
            override fun getDataBindingAdapter() = DataBindingAdapter
        })

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationSub.builder().create(this)
    }

}