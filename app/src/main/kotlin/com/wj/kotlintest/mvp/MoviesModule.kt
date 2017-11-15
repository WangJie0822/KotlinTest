package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPModule
import com.wj.kotlintest.base.OnNetFinishedListener
import com.wj.kotlintest.entity.MoviesListEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 电影相关 Module
 */
class MoviesModule @Inject constructor() : BaseMVPModule() {

    /**
     * 获取评分最高电影列表
     *
     * @param listener 请求回调接口
     */
    fun getHighestRatedMovies(listener: OnNetFinishedListener<MoviesListEntity>): Disposable {
        return netClient
                .getHighestRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener.onSuccess(it) }, { listener.onFail(it) })
    }

    /**
     * 获取最流行电影列表
     *
     * @param listener 请求回调接口
     */
    fun getPopularMovies(listener: OnNetFinishedListener<MoviesListEntity>): Disposable {
        return netClient
                .getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener.onSuccess(it) }, { listener.onFail(it) })
    }

}