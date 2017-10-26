package com.wj.kotlintest.databinding

import android.databinding.BindingAdapter
import android.graphics.BitmapFactory
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
     * @param v        View 对象
     * @param selected selected 状态
     */
    @BindingAdapter("selected")
    fun selected(v: View, selected: Boolean) {
        v.isSelected = selected
    }

    /**
     * 加载方头像图片
     *
     * @param iv     ImageView 对象
     * @param imgUrl 图片 Url
     */
    @BindingAdapter("img_url")
    fun headImg(iv: ImageView, imgUrl: String) {
        GlideApp.with(iv.context)
                .load(imgUrl)
                .thumbnail(0.1f)
                .into(iv)
    }

    /**
     * 根据资源id加载图片
     *
     * @param iv    ImageView 对象
     * @param resId 图片资源id
     */
    @BindingAdapter("res_img")
    fun resImg(iv: ImageView, resId: Int) {
        iv.setImageResource(resId)
    }

    /**
     * 根据图片路径加载图片
     *
     * @param iv      ImageView 对象
     * @param imgPath 图片地址
     */
    @BindingAdapter("path_img")
    fun pathImg(iv: ImageView, imgPath: String) {
        try {
            val `in` = iv.context.assets.open(imgPath)
            val bmp = BitmapFactory.decodeStream(`in`)
            iv.setImageBitmap(bmp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
