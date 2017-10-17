package com.wj.kotlintest

import com.wj.kotlintest.base.BaseMVPModule
import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.base.BaseMVPView

import javax.inject.Inject

/**
 * 空白Presenter
 */
class BlankPresenter @Inject
internal constructor() : BaseMVPPresenter<BaseMVPView, BaseMVPModule>()
