package com.wj.kotlintest.expanding

import com.wj.kotlintest.utils.AppManager

/**
 * 将 dp 单位转换为 px
 *
 * @return dp 对应的 px 值
 */
fun <N : Number> N.dip2px(): Int {
    val density = AppManager.peekActivity().resources.displayMetrics.density
    return when (this) {
        is Int -> (this / density).round()
        is Short -> (this / density).round()
        is Long -> (this / density).round()
        is Float -> (this / density).round()
        is Double -> (this / density).round()
        else -> this.toInt()
    }
}

/**
 * 四舍五入
 *
 * @return 四舍五入后的值
 */
fun <N : Number> N.round(): Int {
    return when(this) {
        is Float -> (this + 0.5F).toInt()
        is Double -> (this + 0.5).toInt()
        else -> this.toInt()
    }
}
