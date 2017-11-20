package com.wj.kotlintest.net

import com.wj.kotlintest.entity.MoviesListEntity
import com.wj.kotlintest.entity.ReviewsEntity
import com.wj.kotlintest.entity.TrailersEntity
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
    @GET(UrlDefinition.GET_HIGHEST_RATED_MOVIES)
    fun getHighestRatedMovies(): Observable<MoviesListEntity>

    /**
     * 获取最流行的电影
     */
    @GET(UrlDefinition.GET_POPULAR_MOVIES)
    fun getPopularMovies(): Observable<MoviesListEntity>

    /**
     * 获取特别收录信息
     *
     * @param url 请求链接
     */
    @GET
    fun getTrailers(@Url url: String): Observable<TrailersEntity>

    /**
     * 获取评论信息
     *
     * @param url 请求链接
     */
    @GET
    fun getReviews(@Url url: String): Observable<ReviewsEntity>
}