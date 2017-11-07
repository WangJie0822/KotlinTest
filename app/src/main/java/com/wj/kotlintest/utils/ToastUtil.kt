package com.wj.kotlintest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * Toast 工具类
 *
 * @author 王杰
 */
@SuppressLint("StaticFieldLeak")
object ToastUtil {

    private lateinit var mContext: Context

    fun bindContext(context: Context) {
        mContext = context
    }

    fun show(str: String) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show()
    }

    fun show(@StringRes strResID: Int) {
        show(mContext.getString(strResID))
    }
}