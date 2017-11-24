package com.wj.kotlintest.databinding

import android.databinding.BindingAdapter
import android.graphics.BitmapFactory
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.wj.kotlintest.glide.GlideApp

/**
 * DataBinding适配器
 */
object DataBindingAdapter {

    /**
     * 设置 View selected 状态
     *
     * @param v        [View] 对象
     * @param selected selected 状态
     */
    @BindingAdapter("app:selected")
    fun selected(v: View, selected: Boolean) {
        v.isSelected = selected
    }

    /**
     * 加载图片
     *
     * @param iv     [ImageView] 对象
     * @param imgUrl 图片 Url
     * @param resID 占位图片资源 id
     */
    @BindingAdapter("app:img_url", "app:img_placeholder", requireAll = false)
    fun imgUrl(iv: ImageView, imgUrl: String, resID: Int) {
        if (!TextUtils.isEmpty(imgUrl)) {
            val glideRequest = GlideApp.with(iv.context)
                    .load(imgUrl)
            if (0 != resID) {
                glideRequest.placeholder(resID)
            }
            glideRequest.into(iv)
        }
    }

    /**
     * 根据资源 id 加载图片
     *
     * @param iv    [ImageView] 对象
     * @param resID 图片资源 id
     */
    @BindingAdapter("android:src")
    fun src(iv: ImageView, resID: Int) {
        if (0 != resID) {
            iv.setImageResource(resID)
        }
    }

    /**
     * 根据图片路径加载图片
     *
     * @param iv      [ImageView] 对象
     * @param imgPath 图片地址
     */
    @BindingAdapter("app:img_path")
    fun imgPath(iv: ImageView, imgPath: String) {
        try {
            val input = iv.context.assets.open(imgPath)
            val bmp = BitmapFactory.decodeStream(input)
            iv.setImageBitmap(bmp)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 长按事件处理
     *
     * @param v [View] 对象
     * @param listener [OnAdapterLongClickListener] 事件监听对象
     */
    @BindingAdapter("android:onLongClick")
    fun longClick(v: View, listener: OnAdapterLongClickListener) {
        v.setOnLongClickListener {
            listener.onLongClick()
            true
        }
    }

    interface OnAdapterLongClickListener {
        fun onLongClick()
    }

    /**
     * 设置 Toolbar Flag，使其在布局向上滚动时自动隐藏
     *
     * @param toolbar [Toolbar]
     * @param canHide 能否自动隐藏
     */
    @BindingAdapter("app:canHide")
    fun canToolbarHide(toolbar: Toolbar, canHide: Boolean) {
        val layoutParams = toolbar.layoutParams as? AppBarLayout.LayoutParams
        layoutParams?.let {
            it.scrollFlags = if (canHide) {
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
            } else {
                0
            }
            toolbar.layoutParams = it
        }
    }

    /**
     * 设置 Toolbar 左侧按钮图片
     *
     * @param toolbar [Toolbar]
     * @param leftResID 图片资源 id
     */
    @BindingAdapter("app:leftIcon")
    fun leftIcon(toolbar: Toolbar, leftResID: Int) {
        if (0 != leftResID) {
            toolbar.setNavigationIcon(leftResID)
        }
    }

    /**
     * 设置 Toolbar 左侧按钮点击事件
     *
     * @param toolbar [Toolbar]
     * @param listener [OnAdapterLeftClickListener] 事件监听对象
     */
    @BindingAdapter("app:onLeftClick")
    fun leftClick(toolbar: Toolbar, listener: OnAdapterLeftClickListener) {
        toolbar.setNavigationOnClickListener {
            listener.onLeftClick()
        }
    }

    interface OnAdapterLeftClickListener {
        fun onLeftClick()
    }

    /**
     * 设置 Floating 按钮锚点
     *
     * @param floating [FloatingActionButton]
     * @param id 锚点 View id
     */
    @BindingAdapter("android:layout_anchor")
    fun floatingAnchor(floating: FloatingActionButton, id: Int) {
        val layoutParams = floating.layoutParams as? CoordinatorLayout.LayoutParams
        layoutParams?.let {
            it.anchorId = id
            floating.layoutParams = it
        }
    }

    /**
     * 设置 Floating 按钮重心
     *
     * @param floating [FloatingActionButton]
     * @param gravity 重心值 [android.view.Gravity] 使用 or 计算
     */
    @BindingAdapter("android:layout_gravity")
    fun floatingGravity(floating: FloatingActionButton, gravity: Int) {
        val layoutParams = floating.layoutParams as? CoordinatorLayout.LayoutParams
        layoutParams?.let {
            it.anchorGravity = gravity
            floating.layoutParams = it
        }
    }

    /**
     * 设置控件是否可见
     *
     * @param v [View] 控件
     * @param visible 是否可见
     */
    @BindingAdapter("app:visibility")
    fun setVisibility(v: View, visible: Boolean) {
        v.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
