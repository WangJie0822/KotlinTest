package com.wj.kotlintest.mvp

import com.wj.kotlintest.base.BaseMVPModule
import com.wj.kotlintest.base.BaseMVPPresenter
import com.wj.kotlintest.bean.MoviesListEntity
import com.wj.kotlintest.net.UrlDefinition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *
 *
 * @author 王杰
 */
class MoviesModule @Inject constructor() : BaseMVPModule() {

    fun getHighestRatedMovies(listener: BaseMVPPresenter.OnNetFinishListener<MoviesListEntity>): Disposable {
        return netClient
                .getHighestRatedMovies(UrlDefinition.GET_HIGHEST_RATED_MOVIES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener.onSuccess(it) }, { listener.onFail(it) })
    }

}