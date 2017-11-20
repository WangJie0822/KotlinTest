package com.wj.kotlintest.utils

import android.support.annotation.StringRes
import android.widget.Toast

/**
 * Toast 工具类
 */
object ToastUtil {

    /**
     * 弹 Toast
     *
     * @param strResID 字符串资源 id
     */
    fun show(@StringRes strResID: Int) {
        show(AppManager.peekActivity().getString(strResID))
    }

    /**
     * 弹 Toast
     *
     * @param str 字符串
     */
    fun show(str: String) {
        Toast.makeText(AppManager.peekActivity(), str, Toast.LENGTH_SHORT).show()
    }
}