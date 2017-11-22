package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.base.BaseMVPView
import com.wj.kotlintest.entity.MoviesEntity
import javax.inject.Inject

/**
 * 最喜欢的电影 Presenter
 */
class FavoritePresenter @Inject constructor() : BaseMVPPresenter<FavoriteView, MoviesModule>() {

    /**
     * 获取最喜欢的电影列表
     */
    fun getFavoriteMoviesList() {
        mView?.let {
            val moviesList = mModule.getFavoriteMovies()

            if (moviesList.isEmpty()) {
                // 没有数据
                it.noData()
            } else {
                // 更新界面
                it.netFinished()
                it.notifyList(moviesList)
            }
        }
    }
}

/**
 * 最喜欢的电影 View
 */
interface FavoriteView: BaseMVPView {

    /**
     * 获取成功，更新界面
     *
     * @param list 最喜欢的电影列表
     */
    fun notifyList(list: ArrayList<MoviesEntity>)
}