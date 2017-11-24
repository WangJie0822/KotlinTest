package com.wj.kotlintest.utils

import android.content.Context
import com.wj.kotlintest.application.MyApplication

/**
 * SharedPreferences 工具类
 */
object SharedPrefUtil {

    /** SharedPreferences 文件名 */
    private val SP_FILE_NAME = "KOTLIN_SHARED_PREF"
    /** SharedPreferences 对象 */
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
    fun getString(key: String, defValue: String) = sharedPref.getString(key, defValue)

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
    fun getInteger(key: String, defValue: Int) = sharedPref.getInt(key, defValue)

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
    fun getBoolean(key: String, defValue: Boolean) = sharedPref.getBoolean(key, defValue)

    /**
     * 根据 key，从 SharedPref 中移除数据
     *
     * @param key 键名
     */
    fun remove(key: String) {
        sharedPref.edit().remove(key).apply()
    }

    /**
     * 获取 SharedPref 中所有数据集合
     *
     * @return 保存的所有数据集合
     */
    fun getAll(): Map<String, *> = sharedPref.all

}