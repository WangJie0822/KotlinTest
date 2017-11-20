package com.wj.kotlintest.mvp

import android.util.Log
import com.orhanobut.logger.Logger
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
        // 绑定 View 之后才能调用
        mView?.let {
            val dispose = mModule.getHighestRatedMovies(object : OnNetFinishedListener<MoviesListEntity> {
                override fun onSuccess(entity: MoviesListEntity) {
                    // 请求成功，恢复显示状态
                    it.netFinished()
                    // 更新数据
                    it.notifyData(entity)
                }

                override fun onFailed(throwable: Throwable) {
                    // 请求失败，显示网络异常界面
                    it.netError()
                    // 打印日志
                    Logger.e(throwable, "HIGHEST_RATED_MOVIES")
                }
            })
            addDisposable(dispose)
        }
    }

    /**
     * 获取最流行电影列表
     */
    fun getPopularMovies() {
        // 绑定 View 之后才能调用
        mView?.let {
            val dispose = mModule.getPopularMovies(object : OnNetFinishedListener<MoviesListEntity> {
                override fun onSuccess(entity: MoviesListEntity) {
                    // 请求成功，恢复显示状态
                    it.netFinished()
                    // 更新数据
                    it.notifyData(entity)
                }

                override fun onFailed(throwable: Throwable) {
                    // 请求失败，显示网络异常界面
                    it.netError()
                    // 打印日志
                    Log.e("NET_ERROR", "HIGHEST_RATED_MOVIES", throwable)
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
    /**
     * 更新数据
     *
     * @param data 电影列表数据
     */
    fun notifyData(data: MoviesListEntity)
}