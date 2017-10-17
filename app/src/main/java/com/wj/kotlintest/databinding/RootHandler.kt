package com.wj.kotlintest.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.annotation.DrawableRes

import com.wj.kotlintest.BR

/**
 * 根布局 Handler
 */
class RootHandler(private val listener: OnTitleClickListener) : BaseObservable() {

    /** 标记-是否显示标题栏  */
    @get:Bindable
    var isShowTitle: Boolean = false
        set(showTitle) {
            field = showTitle
            notifyPropertyChanged(BR.showTitle)
        }

    /** 标记-是否显示左侧图标  */
    @get:Bindable
    var isShowIvLeft: Boolean = false
        set(showIvLeft) {
            field = showIvLeft
            notifyPropertyChanged(BR.showIvLeft)
        }

    /** 标记-是否显示右侧图标  */
    @get:Bindable
    var isShowIvRight: Boolean = false
        set(showIvRight) {
            field = showIvRight
            notifyPropertyChanged(BR.showIvRight)
        }

    /** 标记-是否显示标题  */
    @get:Bindable
    var isShowTvTitle: Boolean = false
        set(showTvTitle) {
            field = showTvTitle
            notifyPropertyChanged(BR.showTvTitle)
        }

    /** 标记-是否显示右侧文字  */
    @get:Bindable
    var isShowTvRight: Boolean = false
        set(showTvRight) {
            field = showTvRight
            notifyPropertyChanged(BR.showTvRight)
        }

    /** 标记-是否显示网络异常  */
    @get:Bindable
    var isShowNetError: Boolean = false
        set(showNetError) {
            field = showNetError
            notifyPropertyChanged(BR.showNetError)
        }

    /** 标记-是否显示无数据  */
    @get:Bindable
    var isShowNoData: Boolean = false
        set(showNoData) {
            field = showNoData
            notifyPropertyChanged(BR.showNoData)
        }

    /** 标记-是否显示加载中  */
    @get:Bindable
    var isShowLoading: Boolean = false
        set(showLoading) {
            field = showLoading
            notifyPropertyChanged(BR.showLoading)
        }

    /** 左侧按钮图片id  */
    @get:Bindable
    var ivLeftResId: Int = 0
        set(@DrawableRes ivLeftRedId) {
            field = ivLeftRedId
            notifyPropertyChanged(BR.ivLeftResId)
        }

    /** 右侧按钮图片id  */
    @get:Bindable
    var ivRightResId: Int = 0
        set(@DrawableRes ivRightResId) {
            field = ivRightResId
            notifyPropertyChanged(BR.ivRightResId)
        }

    /** 标题文本  */
    @get:Bindable
    var tvTitle: String? = null
        set(tvTitle) {
            field = tvTitle
            notifyPropertyChanged(BR.tvTitle)
        }

    /** 右侧文本  */
    @get:Bindable
    var tvRight: String? = null
        set(tvRight) {
            field = tvRight
            notifyPropertyChanged(BR.tvRight)
        }

    /**
     * 左侧按钮、文本、图片点击事件
     */
    fun onLeftClick() {
        listener.onLeftClick()
    }

    /**
     * 右侧按钮、文本、图片点击事件
     */
    fun onRightClick() {
        listener.onRightClick()
    }

    /**
     * 无数据界面点击事件
     */
    fun onNoDataClick() {
        listener.onNoDataClick()
    }

    /**
     * 无网络界面点击事件
     */
    fun onNetErrorClick() {
        listener.onNetErrorClick()
    }

    interface OnTitleClickListener {

        fun onLeftClick()

        fun onRightClick()

        fun onNoDataClick()

        fun onNetErrorClick()
    }
}
