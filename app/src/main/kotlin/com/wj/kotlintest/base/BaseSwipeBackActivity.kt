package com.wj.kotlintest.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.wj.swipelayout.SwipeBackActivityBase
import com.wj.swipelayout.SwipeBackActivityHelper
import com.wj.swipelayout.SwipeBackLayout
import com.wj.swipelayout.Utils

/**
 * Activity 基类，右划返回
 */
abstract class BaseSwipeBackActivity<P : BaseMVPPresenter<*, *>, DB : ViewDataBinding>
    : BaseActivity<P, DB>(), SwipeBackActivityBase {

    private lateinit var mHelper: SwipeBackActivityHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout = mHelper.swipeBackLayout

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }


}