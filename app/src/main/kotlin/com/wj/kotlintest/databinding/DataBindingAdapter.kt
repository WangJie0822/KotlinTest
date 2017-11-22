package com.wj.kotlintest.databinding

import android.databinding.BindingAdapter
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.wj.kotlintest.R
import com.wj.kotlintest.glide.GlideApp

/**
 * DataBinding适配器
 */
object DataBindingAdapter {

    /**
     * 设置 View selected 状态
     *
     * @param v        View 对象
     * @param selected selected 状态
     */
    @BindingAdapter("app:selected")
    fun selected(v: View, selected: Boolean) {
        v.isSelected = selected
    }

    /**
     * 加载方头像图片
     *
     * @param iv     ImageView 对象
     * @param imgUrl 图片 Url
     */
    @BindingAdapter("app:img_url")
    fun imgUrl(iv: ImageView, imgUrl: String) {
        GlideApp.with(iv.context)
                .load(imgUrl)
                .placeholder(R.mipmap.img_default)
                .into(iv)
    }

    /**
     * 根据资源id加载图片
     *
     * @param iv    ImageView 对象
     * @param resId 图片资源id
     */
    @BindingAdapter("app:res_img")
    fun resImg(iv: ImageView, resId: Int) {
        iv.setImageResource(resId)
    }

    /**
     * 根据图片路径加载图片
     *
     * @param iv      ImageView 对象
     * @param imgPath 图片地址
     */
    @BindingAdapter("app:path_img")
    fun pathImg(iv: ImageView, imgPath: String) {
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
     * @param v View 对象
     * @param listener 事件监听对象
     */
    @BindingAdapter("app:long_click")
    fun longClick(v: View, listener: OnAdapterLongClickListener) {
        v.setOnLongClickListener {
            listener.onLongClick()
            true
        }
    }

    interface OnAdapterLongClickListener {
        fun onLongClick()
    }

}
