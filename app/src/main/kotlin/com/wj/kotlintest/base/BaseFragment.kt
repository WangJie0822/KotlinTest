package com.wj.kotlintest.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wj.kotlintest.R
import com.wj.kotlintest.databinding.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Fragment基类
 */
abstract class BaseFragment<P : BaseMVPPresenter<*, *>, DB : ViewDataBinding>
    : DaggerFragment(),
        BaseMVPView,
        OnBaseClickListener {

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: AppCompatActivity

    /** 当前界面 Presenter 对象 */
    @Inject
    protected lateinit var presenter: P

    /** 根布局 DataBinding 对象 */
    protected lateinit var baseBinding: LayoutBaseBinding
    /** 当前界面布局 DataBinding 对象 */
    protected lateinit var mBinding: DB

    /**
     * 重写 onCreate() 方法，初始化当前 Context 对象，打印信息
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 保存当前 Context 对象
        mContext = activity as AppCompatActivity

        Log.w("Fragment---->>", "create---->>$this")
    }

    /**
     * 重写 onCreateView() 方法，加载根布局、当前界面布局及相关初始化
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 加载根布局，初始化 DataBinding
        baseBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.layout_base, null, false
        )
        // 绑定事件处理
        baseBinding.handler = RootHandler(this)

        // 加载布局，初始化 DataBinding
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                layoutResID(), null, false
        )

        // 将当前布局添加到根布局
        baseBinding.flContent.removeAllViews()
        baseBinding.flContent.addView(mBinding.root)

        // 初始化标题栏
        initTitleBar()

        // 初始化浮动按钮
        initFloatingButton()

        // 初始化布局
        initView()

        // 设置布局
        return baseBinding.root
    }

    /**
     * 重写 onDestroy() 方法，MVP 生命周期管理、打印信息
     */
    override fun onDestroy() {

        // 界面销毁时，消费所有事件，清空引用
        presenter.dispose()
        presenter.detach()

        // 打印信息
        Log.w("Fragment---->>", "destroy---->$this")

        super.onDestroy()
    }

    /**
     * 绑定布局
     *
     * @return 当前界面布局id
     */
    @LayoutRes
    protected abstract fun layoutResID(): Int

    /**
     * 初始化布局
     */
    protected abstract fun initView()

    /**
     *  设置 Toolbar
     */
    open protected fun setToolbar() {
        setHasOptionsMenu(true)
        // 添加 Toolbar
        mContext.setSupportActionBar(baseBinding.toolbar)
        // 隐藏默认 title
        mContext.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /**
     * 初始化标题栏
     */
    open protected fun initTitleBar() {}

    /**
     * 初始化浮动按钮
     */
    open protected fun initFloatingButton() {}

    /**
     * 设置显示 Floating 按钮
     *
     * @param show 是否显示
     */
    open protected fun showFloating(show: Boolean = true) {
        baseBinding.handler?.showFloating = show
    }

    /**
     * 设置 Floating 按钮图片 id
     *
     * @param resID 图片资源 id
     */
    open protected fun setFloatingResID(@DrawableRes resID: Int) {
        baseBinding.handler?.floatingResID = resID
    }

    /**
     * 设置 Floating 按钮锚点
     *
     * @param id 锚点 View id
     */
    open protected fun setFloatingAnchor(@IdRes id: Int) {
        baseBinding.handler?.floatingAnchor = id
    }

    /**
     * 设置 Floating 按钮重心
     *
     * @param gravity 重心 [android.view.Gravity]
     */
    open protected fun setFloatingGravity(gravity: Int) {
        baseBinding.handler?.floatingGravity = gravity
    }

    /**
     * 设置 Floating 按钮选中状态
     *
     * @param selected 是否选中
     */
    open protected fun setFloatingSelected(selected: Boolean) {
        baseBinding.handler?.floatingSelected = selected
    }

    /**
     * 获取 Floating 按钮选中状态
     *
     * @return 选中状态
     */
    open protected fun isFloatingSelected() = baseBinding.handler!!.floatingSelected

    /**
     * 设置 Floating 按钮点击事件监听
     *
     * @param listener 事件监听对象
     */
    open protected fun setFloatingClick(listener: OnFloatingClickListener) {
        baseBinding.handler?.floatingClickListener = listener
    }

    /**
     * 设置 Floating 按钮长按事件监听
     *
     * @param listener 事件监听对象
     */
    open protected fun setFloatingLongClick(listener: OnFloatingLongClickListener) {
        baseBinding.handler?.floatingLongClickListener = listener
    }

    /**
     * 设置标题栏显示
     *
     * @param showTitle 是否显示
     */
    protected fun showTitleBar(showTitle: Boolean = true) {
        baseBinding.handler?.showTitleBar = showTitle
        if (showTitle) {
            setToolbar()
        }
    }

    /**
     * 设置标题栏能否隐藏
     *
     * @param canHide 能否隐藏
     */
    protected fun setToolbarHide(canHide: Boolean = true) {
        baseBinding.handler?.canToolbarHide = canHide
    }

    /**
     * 设置标题文本
     *
     * @param strResID 标题文本资源id
     */
    protected fun setTitleStr(@StringRes strResID: Int) {
        setTitleStr(getString(strResID))
    }

    /**
     * 设置标题文本
     *
     * @param str      标题文本
     */
    protected fun setTitleStr(str: String) {
        baseBinding.handler?.showTitleStr = true
        baseBinding.handler?.titleStr = str
    }

    /**
     * 设置标题栏左侧图标，默认返回按钮
     *
     * @param resID     标题栏左侧图标资源id，默认返回按钮
     */
    protected fun setIvLeft(@DrawableRes resID: Int = 0) {
        if (0 == resID) {
            // 使用默认图标
            mContext.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            // 使用指定图标
            baseBinding.handler?.ivLeftResID = resID
        }
    }

    /**
     * 重写BaseMvpView中方法，网络异常时调用
     */
    override fun netError() {
        val handler = baseBinding.handler
        handler?.let {
            if (it.showNoData) {
                it.showNoData = false
            }
            if (it.showLoading) {
                val drawable = baseBinding.ivLoading.drawable
                (drawable as? AnimationDrawable)?.stop()
                it.showLoading = false
            }
            if (!it.showNetError) {
                it.showNetError = true
            }
            listComplete()
        }
    }

    /**
     * 重写BaseMvpView中方法，无数据时调用
     */
    override fun noData() {
        val handler = baseBinding.handler
        handler?.let {
            if (it.showNetError) {
                it.showNetError = false
            }
            if (it.showLoading) {
                val drawable = baseBinding.ivLoading.drawable
                (drawable as? AnimationDrawable)?.stop()
                it.showLoading = false
            }
            if (!it.showNoData) {
                it.showNoData = true
            }
            listComplete()
        }
    }

    /**
     * 重写BaseMvpView中方法，加载数据时调用
     */
    override fun loading() {
        val handler = baseBinding.handler
        handler?.let {
            if (it.showNetError) {
                it.showNetError = false
            }
            if (it.showNoData) {
                it.showNoData = false
            }
            if (!it.showLoading) {
                val drawable = baseBinding.ivLoading.drawable
                (drawable as? AnimationDrawable)?.start()
                it.showLoading = true
            }
        }
    }

    /**
     * 重写BaseMvpView中方法，网络请求结束后调用，隐藏其他界面
     */
    override fun netFinished() {
        val handler = baseBinding.handler
        handler?.let {
            if (it.showNetError) {
                it.showNetError = false
            }
            if (it.showNoData) {
                it.showNoData = false
            }
            if (it.showLoading) {
                val drawable = baseBinding.ivLoading.drawable
                (drawable as? AnimationDrawable)?.stop()
                it.showLoading = false
            }
            listComplete()
        }
    }

    /**
     * 使用SwipeToLoadView时重写，完成刷新步骤
     */
    open protected fun listComplete() {}

    /**
     * 标题栏左侧点击事件，默认结束当前界面
     */
    override fun onLeftClick() {}

    /**
     * 无数据界面点击事件，默认显示加载中
     */
    override fun onNoDataClick() {
        loading()
    }

    /**
     * 网络异常界面点击事件，默认显示加载中
     */
    override fun onNetErrorClick() {
        loading()
    }
}