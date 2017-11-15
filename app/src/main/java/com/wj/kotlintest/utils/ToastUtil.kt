package com.wj.kotlintest.utils

import android.support.annotation.StringRes
import android.widget.Toast

/**
 * Toast 工具类
 */
object ToastUtil {

    fun show(@StringRes strResID: Int) {
        show(AppManager.peekActivity().getString(strResID))
    }

    fun show(str: String) {
        Toast.makeText(AppManager.peekActivity(), str, Toast.LENGTH_SHORT).show()
    }
}