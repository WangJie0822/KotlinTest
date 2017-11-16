package com.wj.kotlintest.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wj.kotlintest.BR

/**
 * 根布局 Handler
 */
class RootHandler(private val listener: OnBaseClickListener) : BaseObservable() {

    /** 标记-是否显示标题栏  */
    @get:Bindable
    var showTitle: Boolean = false
        set(showTitle) {
            field = showTitle
            notifyPropertyChanged(BR.showTitle)
        }

    /** 标记-是否显示标题  */
    @get:Bindable
    var showTvTitle: Boolean = false
        set(showTvTitle) {
            field = showTvTitle
            notifyPropertyChanged(BR.showTvTitle)
        }

    /** 标记-是否显示网络异常  */
    @get:Bindable
    var showNetError: Boolean = false
        set(showNetError) {
            field = showNetError
            notifyPropertyChanged(BR.showNetError)
        }

    /** 标记-是否显示无数据  */
    @get:Bindable
    var showNoData: Boolean = false
        set(showNoData) {
            field = showNoData
            notifyPropertyChanged(BR.showNoData)
        }

    /** 标记-是否显示加载中  */
    @get:Bindable
    var showLoading: Boolean = false
        set(showLoading) {
            field = showLoading
            notifyPropertyChanged(BR.showLoading)
        }

    /** 标题文本  */
    @get:Bindable
    var tvTitle: String? = null
        set(tvTitle) {
            field = tvTitle
            notifyPropertyChanged(BR.tvTitle)
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
}

interface OnBaseClickListener {

    fun onNoDataClick()

    fun onNetErrorClick()
}