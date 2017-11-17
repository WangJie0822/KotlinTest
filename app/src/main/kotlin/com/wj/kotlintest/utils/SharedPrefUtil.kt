package com.wj.kotlintest.utils

import android.content.Context
import com.wj.kotlintest.application.MyApplication

/**
 * SharedPreferences 工具类
 */
object SharedPrefUtil {

    private val SP_FILE_NAME = "KOTLIN_SHARED_PREF"

    private val sharedPref = MyApplication.INSTANCE.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)

    /**
     * 保存字符串
     *
     * @param key   键名
     * @param value 值
     */
    fun putString(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    /**
     * 获取字符串
     *
     * @param key      键名
     * @param defValue 默认值
     *
     * @return 字符串
     */
    fun getString(key: String, defValue: String): String? {
        return sharedPref.getString(key, defValue)
    }

    /**
     * 保存整形
     *
     * @param key   键名
     * @param value 值
     */
    fun putInteger(key: String, value: Int) {
        sharedPref.edit().putInt(key, value).apply()
    }

    /**
     * 获取整形
     *
     * @param key      键名
     * @param defValue 默认值
     *
     * @return 整形
     */
    fun getInteger(key: String, defValue: Int): Int {
        return sharedPref.getInt(key, defValue)
    }

    /**
     * 保存布尔类型
     *
     * @param key   键名
     * @param value 值
     */
    fun putBoolean(key: String, value: Boolean) {
        sharedPref.edit().putBoolean(key, value).apply()
    }

    /**
     * 获取布尔值
     *
     * @param key      键名
     * @param defValue 默认值
     *
     * @return 布尔值
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defValue)
    }

}