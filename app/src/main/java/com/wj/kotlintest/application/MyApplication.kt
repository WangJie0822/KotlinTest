//package com.wj.kotlintest.application
//
//import android.app.Activity
//import android.app.Application
//import com.wj.kotlintest.dagger.sub.application.DaggerApplicationSub
//import dagger.android.AndroidInjector
//import dagger.android.DispatchingAndroidInjector
//import dagger.android.HasActivityInjector
//import javax.inject.Inject
//
///**
// *
// *
// * @author 王杰
// */
//class MyApplication: Application(), HasActivityInjector{
//
//    @Inject
//    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
//
//    override fun onCreate() {
//        super.onCreate()
//
//        DaggerApplicationSub
//                .builder()
//                .build()
//                .inject(this)
//
//    }
//
//    override fun activityInjector(): AndroidInjector<Activity> {
//        return activityInjector
//    }
//}