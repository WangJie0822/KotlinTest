package com.wj.kotlintest.mvp;

import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.base.BaseMVPView
import com.wj.kotlintest.entity.MoviesEntity
import javax.inject.Inject

/**
 * 最喜欢的电影列表 Presenter
 */
class FavoritePresenter @Inject constructor() : BaseMVPPresenter<FavoriteView, MoviesModule>() {

    /**
     * 获取最喜欢的电影数据
     */
    fun getFavoriteList() {
        mView?.let {
            // 获取数据
            val list = mModule.getFavoriteMovies()
            if (list.isEmpty()) {
                // 还没有收藏电影
                it.noData()
            } else {
                // 更新界面
                it.netFinished()
                it.notify(list)
            }
        }
    }

}

/**
 * 最喜欢的电影列表 View
 */
interface FavoriteView: BaseMVPView {

    /**
     * 更新界面
     *
     * @param list 最喜欢的电影集合
     */
    fun notify(list: ArrayList<MoviesEntity>)
}