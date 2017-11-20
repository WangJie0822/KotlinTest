package com.wj.kotlintest.mvp

import com.orhanobut.logger.Logger
import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.base.BaseMVPView
import com.wj.kotlintest.base.OnNetFinishedListener
import com.wj.kotlintest.entity.ReviewsEntity
import com.wj.kotlintest.entity.TrailersEntity
import com.wj.kotlintest.utils.ToastUtil
import javax.inject.Inject

/**
 * 电影详情 Presenter
 */
class MoviesDetailsPresenter @Inject constructor() : BaseMVPPresenter<MoviesDetailsView, MoviesModule>() {

    /**
     * 获取特别收录信息
     */
    fun getTrailers() {
        // 绑定 View 之后才能调用
        mView?.let {
            val disposable = mModule.getTrailers(it.getMoviesId(), object : OnNetFinishedListener<TrailersEntity> {
                override fun onSuccess(entity: TrailersEntity) {
                    // 请求成功，更新数据
                    it.notifyTrailers(entity)
                }

                override fun onFailed(throwable: Throwable) {
                    // 请求失败，提示网络异常
                    ToastUtil.show("Net Error!")
                    Logger.e(throwable, "GET_TRAILERS")
                }
            })
            addDisposable(disposable)
        }
    }

    /**
     * 获取评论信息
     */
    fun getReviews() {
        // 绑定 View 之后才能调用
        mView?.let {
            val disposable = mModule.getReviews(it.getMoviesId(), object : OnNetFinishedListener<ReviewsEntity> {
                override fun onSuccess(entity: ReviewsEntity) {
                    // 请求成功，更新数据
                    it.notifyReviews(entity)
                }

                override fun onFailed(throwable: Throwable) {
                    // 请求失败，提示网络异常
                    ToastUtil.show("Net Error!")
                    Logger.e(throwable, "GET_TRAILERS")
                }

            })
            addDisposable(disposable)
        }
    }
}

/**
 * 电影详情 View
 */
interface MoviesDetailsView : BaseMVPView {

    /**
     * 获取电影 id
     *
     * @return 电影 id
     */
    fun getMoviesId(): String

    /**
     * 更新特别收录信息
     *
     * @param entity 特别收录信息
     */
    fun notifyTrailers(entity: TrailersEntity)

    /**
     * 更新评论信息
     *
     * @param entity 评论信息
     */
    fun notifyReviews(entity: ReviewsEntity)
}
