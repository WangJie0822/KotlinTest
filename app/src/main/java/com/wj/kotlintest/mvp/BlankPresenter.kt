package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPModule
import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.base.BaseMVPView

import javax.inject.Inject

/**
 * 空白Presenter
 */
class BlankPresenter @Inject constructor() : BaseMVPPresenter<BaseMVPView, BaseMVPModule>()
