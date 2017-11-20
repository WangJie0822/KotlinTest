package com.wj.kotlintest.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout

/**
 * 状态栏设置工具类
 */
object StatusBarUtil {

    /**
     * 默认透明度
     */
    private val DEFAULT_STATUS_BAR_ALPHA = 112

    /**
     * 设置状态栏颜色
     *
     * @param activity           Activity 对象
     * @param colorResId         状态栏颜色资源id
     * @param alpha              状态栏透明度 0~255
     */
    fun setResColor(activity: Activity, @ColorRes colorResId: Int, alpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        @Suppress("DEPRECATION")
        val color = activity.resources.getColor(colorResId)
        setColor(activity, color, alpha)
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity       Activity 对象
     * @param color          状态栏颜色值
     * @param alpha          状态栏透明度 0~255
     */
    fun setColor(activity: Activity, color: Int, alpha: Int = DEFAULT_STATUS_BAR_ALPHA) {

        // 获取可用的透明度，防止超过 0~255 界限
        val usefulAlpha = when {
            alpha < 0 -> 0
            alpha > 255 -> 255
            else -> alpha
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = calculateStatusColor(color, usefulAlpha)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView = activity.window.decorView as ViewGroup
            val count = decorView.childCount
            if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
                decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, usefulAlpha))
            } else {
                val statusView = createStatusBarView(activity, color, usefulAlpha)
                decorView.addView(statusView)
            }
            setRootView(activity)
        }
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        setRootView(activity)
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun transparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity  Activity 对象
     * @param color     状态栏颜色值
     * @param alpha     透明度
     * @return          状态栏矩形条
     */
    private fun createStatusBarView(activity: Activity, color: Int, alpha: Int): StatusBarView {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        return statusBarView
    }

    /**
     * 设置根布局参数
     *
     * @param activity Activity 对象
     */
    private fun setRootView(activity: Activity) {
        val rootView = activity.findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup
        rootView.fitsSystemWindows = true
        rootView.clipToPadding = true
    }

    /**
     * 获取状态栏高度
     *
     * @param context   Context 对象
     * @return          状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 计算状态栏颜色
     *
     * @param color     颜色值
     * @param alpha     透明度
     * @return          最终的状态栏颜色
     */
    private fun calculateStatusColor(color: Int, alpha: Int): Int {
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }
}

class StatusBarView : View {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context) : super(context) {}
}
