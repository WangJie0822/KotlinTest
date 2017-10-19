package com.wj.kotlintest.mvp

import android.util.Log
import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.bean.MoviesListEntity
import javax.inject.Inject

/**
 *
 *
 * @author 王杰
 */
class MoviesHighestRatedPresenter @Inject constructor() : BaseMVPPresenter<MoviesHighestRatedView, MoviesModule>() {

    fun getHighestRatedMovies() {

        val dispose = mModule.getHighestRatedMovies(object : OnNetFinishListener<MoviesListEntity> {
            override fun onSuccess(entity: MoviesListEntity) {
                mView?.notifyData(entity)
            }

            override fun onFail(fail: Throwable) {
                Log.e("NET_ERROR", "HIGHEST_RATED_MOVIES", fail)
            }
        })
        addDisposable(dispose)

    }
}