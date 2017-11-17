package com.wj.kotlintest.mvp

import android.util.Log
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

    fun getTrailers() {
        mView?.let {
            val disposable = mModule.getTrailers(it.getMoviesId(), object : OnNetFinishedListener<TrailersEntity> {
                override fun onSuccess(entity: TrailersEntity) {
                    it.notifyTrailers(entity)
                }

                override fun onFail(fail: Throwable) {
                    ToastUtil.show("Net Error!")
                    Logger.e(fail, "GET_TRAILERS")
                }
            })
            addDisposable(disposable)
        }
    }

    fun getReviews() {
        mView?.let {
            val disposable = mModule.getReviews(it.getMoviesId(), object : OnNetFinishedListener<ReviewsEntity> {
                override fun onSuccess(entity: ReviewsEntity) {
                    it.notifyReviews(entity)
                }

                override fun onFail(fail: Throwable) {
                    ToastUtil.show("Net Error!")
                    Logger.e(fail, "GET_TRAILERS")
                }

            })
            addDisposable(disposable)
        }
    }
}

interface MoviesDetailsView : BaseMVPView {

    fun getMoviesId(): String

    fun notifyTrailers(entity: TrailersEntity)

    fun notifyReviews(entity: ReviewsEntity)
}
