package com.wj.kotlintest.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.Gravity
import android.view.View
import com.wj.kotlintest.BR

/**
 * 根布局 Handler
 */
open class RootHandler(private val listener: OnBaseClickListener) : BaseObservable() {

    open var floatingClickListener: OnFloatingClickListener? = null

    open var floatingLongClickListener: OnFloatingLongClickListener? = null

    /** 标记-是否显示 Floating 按钮 */
    @get:Bindable
    open var showFloating = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showFloating)
        }

    /** Floating 按钮图片资源id */
    @get:Bindable
    open var floatingResID = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.floatingResID)
        }

    /** Floating 按钮锚点 */
    @get:Bindable
    open var floatingAnchor = View.NO_ID
        set(value) {
            field = value
            notifyPropertyChanged(BR.floatingAnchor)
        }

    /** Floating 按钮 Gravity */
    @get:Bindable
    open var floatingGravity = Gravity.NO_GRAVITY
        set(value) {
            field = value
            notifyPropertyChanged(BR.floatingGravity)
        }

    /** Floating 按钮选中状态 */
    @get:Bindable
    open var floatingSelected = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.floatingSelected)
        }

    /** 标记-是否显示标题栏  */
    @get:Bindable
    open var showTitleBar = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showTitleBar)
        }

    /** 标记-是否显示标题  */
    @get:Bindable
    open var showTitleStr = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showTitleStr)
        }

    /** 标记-是否显示网络异常  */
    @get:Bindable
    open var showNetError = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showNetError)
        }

    /** 标记-是否显示无数据  */
    @get:Bindable
    open var showNoData = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showNoData)
        }

    /** 标记-是否显示加载中  */
    @get:Bindable
    open var showLoading = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showLoading)
        }

    /** 标记- Toolbar 能否隐藏 */
    @get:Bindable
    open var canToolbarHide = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.canToolbarHide)
        }

    /** 左侧按钮资源 id */
    @get:Bindable
    open var ivLeftResID = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.ivLeftResID)
        }

    /** 标题文本  */
    @get:Bindable
    open var titleStr = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleStr)
        }

    /**
     * 左侧按钮点击事件
     */
    open fun onLeftClick() {
        listener.onLeftClick()
    }

    /**
     * 无数据界面点击事件
     */
    open fun onNoDataClick() {
        listener.onNoDataClick()
    }

    /**
     * 无网络界面点击事件
     */
    open fun onNetErrorClick() {
        listener.onNetErrorClick()
    }

    /**
     *  Floating 按钮点击事件
     */
    open fun onFloatingClick() {
        floatingClickListener?.onClick()
    }

    /**
     * Floating 按钮长按事件
     */
    open fun onFloatingLongClick() {
        floatingLongClickListener?.onLongClick()
    }
}

class CollapsingRootHandler(listener: OnBaseClickListener) : RootHandler(listener) {

    /** 头部图片 url 地址 */
    @get:Bindable
    var ivHeaderUrl = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.ivHeaderUrl)
        }

    /** 头部图片资源 id */
    @get:Bindable
    var ivHeaderResID = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.ivHeaderResID)
        }
}

/**
 * 根布局点击事件监听接口
 */
interface OnBaseClickListener {

    /**
     * 左侧按钮点击事件
     */
    fun onLeftClick()

    /**
     * 无数据界面点击
     */
    fun onNoDataClick()

    /**
     * 网络异常界面点击
     */
    fun onNetErrorClick()
}

interface OnFloatingClickListener {

    fun onClick()
}

interface OnFloatingLongClickListener {
    fun onLongClick()
}