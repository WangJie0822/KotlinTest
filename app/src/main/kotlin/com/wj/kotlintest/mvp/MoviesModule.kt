package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPModule
import com.wj.kotlintest.base.OnNetFinishedListener
import com.wj.kotlintest.entity.MoviesListEntity
import com.wj.kotlintest.entity.ReviewsEntity
import com.wj.kotlintest.entity.TrailersEntity
import com.wj.kotlintest.net.UrlDefinition
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
                .subscribe({ listener.onSuccess(it) }, { listener.onFailed(it) })
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
                .subscribe({ listener.onSuccess(it) }, { listener.onFailed(it) })
    }

    /**
     * 获取特别收录信息
     *
     * @param id 电影 id
     * @param listener 请求回调接口
     */
    fun getTrailers(id: String, listener: OnNetFinishedListener<TrailersEntity>): Disposable {
        return netClient
                .getTrailers(String.format(UrlDefinition.GET_TRAILERS, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener.onSuccess(it) }, { listener.onFailed(it) })
    }

    /**
     * 获取评论信息
     *
     * @param id 电影 id
     * @param listener 请求回调接口
     */
    fun getReviews(id: String, listener: OnNetFinishedListener<ReviewsEntity>): Disposable {
        return netClient
                .getReviews(String.format(UrlDefinition.GET_REVIEWS, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener.onSuccess(it) }, { listener.onFailed(it) })
    }

}