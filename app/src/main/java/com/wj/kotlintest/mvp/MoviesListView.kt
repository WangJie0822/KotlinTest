package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPView
import com.wj.kotlintest.entity.MoviesListEntity

/**
 *
 *
 * @author 王杰
 */
interface MoviesListView : BaseMVPView {

    fun notifyData(data: MoviesListEntity)
}