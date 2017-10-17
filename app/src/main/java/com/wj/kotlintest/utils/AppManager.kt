package com.wj.kotlintest.utils

import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.util.*

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
class AppManager private constructor() {

    companion object {
        val INSTANCE: AppManager = AppManager()
    }

    /** 保存 Activity 对象的堆栈 */
    private val activityStack: Stack<AppCompatActivity> = Stack()

    /**
     * 添加 Activity 到堆栈
     *
     * @param activity Activity 对象
     */
    fun addActivity(activity: AppCompatActivity) {
        activityStack.add(activity)
        Log.w("AppManager---->>", "add---->>$activity size---->>${activityStack.size}")
    }

    /**
     * 将 Activity 从堆栈移除
     *
     * @param activity Activity 对象
     */
    fun removeActivity(activity: AppCompatActivity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity)
        }
    }
}