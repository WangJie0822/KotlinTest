package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPModule
import com.wj.kotlintest.base.OnNetFinishedListener
import com.wj.kotlintest.entity.MoviesListEntity
import com.wj.kotlintest.net.UrlDefinition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 电影相关 Module
 *
 * @author 王杰
 */
class MoviesModule @Inject constructor() : BaseMVPModule() {

    /**
     * 获取评分最高电影列表
     *
     * @param listener 请求回调接口
     */
    fun getHighestRatedMovies(listener: OnNetFinishedListener<MoviesListEntity>): Disposable {
        return netClient
                .getHighestRatedMovies(UrlDefinition.GET_HIGHEST_RATED_MOVIES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener.onSuccess(it) }, { listener.onFail(it) })
    }

}