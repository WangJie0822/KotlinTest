package com.wj.kotlintest.mvp

import android.util.Log
import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.base.BaseMVPView
import com.wj.kotlintest.base.OnNetFinishedListener
import com.wj.kotlintest.entity.MoviesListEntity
import javax.inject.Inject

/**
 * 电影列表界面 Presenter
 */
class MoviesListPresenter @Inject constructor() : BaseMVPPresenter<MoviesListView, MoviesModule>() {

    /**
     * 获取评分最高电影列表
     */
    fun getHighestRatedMovies() {
        mView?.let {
            val dispose = mModule.getHighestRatedMovies(object : OnNetFinishedListener<MoviesListEntity> {
                override fun onSuccess(entity: MoviesListEntity) {
                    it.onNetFinished()
                    it.notifyData(entity)
                }

                override fun onFail(fail: Throwable) {
                    Log.e("NET_ERROR", "HIGHEST_RATED_MOVIES", fail)
                }
            })

            addDisposable(dispose)
        }
    }

    /**
     * 获取最流行电影列表
     */
    fun getPopularMovies() {

        mView?.let {
            val dispose = mModule.getPopularMovies(object : OnNetFinishedListener<MoviesListEntity> {
                override fun onSuccess(entity: MoviesListEntity) {
                    it.onNetFinished()
                    it.notifyData(entity)
                }

                override fun onFail(fail: Throwable) {
                    Log.e("NET_ERROR", "HIGHEST_RATED_MOVIES", fail)
                }
            })

            addDisposable(dispose)
        }
    }
}

/**
 * 电影列表界面 View
 */
interface MoviesListView : BaseMVPView {

    fun notifyData(data: MoviesListEntity)
}