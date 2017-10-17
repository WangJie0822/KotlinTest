package com.wj.kotlintest.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * DataBinding适配器
 */
@SuppressWarnings("all")
public class DataBindingAdapter {

    /**
     * 设置 View selected 状态
     *
     * @param v        View 对象
     * @param selected selected 状态
     */
    @BindingAdapter("selected")
    public static void selected(View v, boolean selected) {
        v.setSelected(selected);
    }

    /**
     * 加载产品图片
     *
     * @param iv     ImageView 对象
     * @param imgUrl 图片 Url
     */
    @BindingAdapter("product_img")
    public static void productImg(ImageView iv, String imgUrl) {
//        GlideApp.with(iv.getContext())
//                .load(imgUrl)
//                .placeholder(R.mipmap.product_img_default)
//                .thumbnail(0.1f)
//                .into(iv);
    }

    /**
     * 加载方头像图片
     *
     * @param iv     ImageView 对象
     * @param imgUrl 图片 Url
     */
    @BindingAdapter("head_img")
    public static void headImg(ImageView iv, String imgUrl) {
//        GlideApp.with(iv.getContext())
//                .load(imgUrl)
//                .placeholder(R.mipmap.head_img_default)
//                .into(iv);
    }

    /**
     * 加载圆形头像图标
     *
     * @param iv     ImageView 对象
     * @param imgUrl 图片 Url
     */
    @BindingAdapter("icon_img")
    public static void iconImg(ImageView iv, String imgUrl) {
//        GlideApp.with(iv.getContext())
//                .load(imgUrl)
//                .placeholder(R.mipmap.head_icon_default)
//                .into(iv);
    }

    /**
     * 根据资源id加载图片
     *
     * @param iv    ImageView 对象
     * @param resId 图片资源id
     */
    @BindingAdapter("res_img")
    public static void resImg(ImageView iv, int resId) {
        iv.setImageResource(resId);
    }

    /**
     * 根据图片路径加载图片
     *
     * @param iv      ImageView 对象
     * @param imgPath 图片地址
     */
    @BindingAdapter("path_img")
    public static void pathImg(ImageView iv, String imgPath) {
        try {
            InputStream in = iv.getContext().getAssets().open(imgPath);
            Bitmap bmp = BitmapFactory.decodeStream(in);
            iv.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
