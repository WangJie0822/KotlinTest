package com.wj.kotlintest.net

import com.wj.kotlintest.entity.MoviesListEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * 网络请求方法体
 */
interface NetApi {

    /**
     * 获取评价最高电影
     */
    @GET
    fun getHighestRatedMovies(@Url url: String): Observable<MoviesListEntity>
}