package com.wj.kotlintest.application

import android.annotation.SuppressLint
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import com.wj.kotlintest.dagger.DaggerApplicationSub
import com.wj.kotlintest.databinding.DataBindingAdapter
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Application 类
 *
 * @author 王杰
 */
class MyApplication : DaggerApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: MyApplication
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        DataBindingUtil.setDefaultComponent(object : DataBindingComponent{
            override fun getDataBindingAdapter() = DataBindingAdapter
        })

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationSub.builder().create(this)
    }

}