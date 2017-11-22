package com.wj.kotlintest.mvp

import com.google.gson.Gson
import com.wj.kotlintest.base.BaseMVPModule
import com.wj.kotlintest.base.OnNetFinishedListener
import com.wj.kotlintest.constants.FAVORITE_KEY
import com.wj.kotlintest.entity.MoviesEntity
import com.wj.kotlintest.entity.MoviesListEntity
import com.wj.kotlintest.entity.ReviewsEntity
import com.wj.kotlintest.entity.TrailersEntity
import com.wj.kotlintest.net.UrlDefinition
import com.wj.kotlintest.utils.SharedPrefUtil
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

    /**
     * 获取最喜欢的电影列表
     *
     * @return 最喜欢的电影列表
     */
    fun getFavoriteMovies(): ArrayList<MoviesEntity> {
        // 声明集合保存电影列表
        val moviesList = arrayListOf<MoviesEntity>()
        // 获取 SharedPref 保存的所有数据
        val allMap = SharedPrefUtil.getAll()
        // 遍历集合
        for ((key, value) in allMap) {
            // 将关键字通过 "_" 下划线分割
            val splitKey = key.split("_")
            if (splitKey[0] == FAVORITE_KEY) {
                // 与最喜欢的电影关键字匹配
                val entity = Gson().fromJson(value.toString(), MoviesEntity::class.java)
                // 添加到集合保存
                moviesList.add(entity)
            }
        }
        return  moviesList
    }

}