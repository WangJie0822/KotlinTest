package com.wj.kotlintest.net

import com.wj.kotlintest.entity.MoviesListEntity
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * 网络请求方法体
 */
interface NetApi {

    /**
     * 获取评价最高电影
     */
    @GET(UrlDefinition.GET_HIGHEST_RATED_MOVIES)
    fun getHighestRatedMovies(): Observable<MoviesListEntity>

    /**
     * 获取最流行的电影
     */
    @GET(UrlDefinition.GET_POPULAR_MOVIES)
    fun getPopularMovies(): Observable<MoviesListEntity>
}