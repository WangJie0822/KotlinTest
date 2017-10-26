package com.wj.kotlintest.application

import android.annotation.SuppressLint
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import com.wj.kotlintest.dagger.DaggerApplicationSub
import com.wj.kotlintest.databinding.DataBindingAdapter
import com.wj.kotlintest.utils.ToastUtil
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

        ToastUtil.bindContext(this)

        DataBindingUtil.setDefaultComponent(object : DataBindingComponent {
            override fun getDataBindingAdapter(): DataBindingAdapter {
                return DataBindingAdapter
            }
        })

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationSub.builder().create(this)
    }

}