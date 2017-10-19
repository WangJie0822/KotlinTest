package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPView
import com.wj.kotlintest.bean.MoviesListEntity

/**
 *
 *
 * @author 王杰
 */
interface MoviesHighestRatedView : BaseMVPView {

    fun notifyData(data: MoviesListEntity)
}