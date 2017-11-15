package com.wj.kotlintest.net

import com.wj.kotlintest.BuildConfig

/**
 * 网络接口地址
 *
 * @author 王杰
 */
object UrlDefinition {

    private val API_RELEASE = "www.baidu.com"

    private val API_DEBUG = "www.baidu.com"

    private val API_DOMAIN = if (BuildConfig.DEBUG) API_DEBUG else API_RELEASE

    private val USE_SSL = false

    private val SCHEME = if (USE_SSL) "https://" else "http://"

    private const val API_KEY = BuildConfig.API_KEY // add your API key here

    val BASE_URL = SCHEME + API_DOMAIN

    val POSTER_PATH = "http://image.tmdb.org/t/p/w342"

    const val GET_HIGHEST_RATED_MOVIES = "http://api.themoviedb.org/3/discover/movie?vote_count.gte=500&language=zh&sort_by=vote_average.desc&api_key=" + API_KEY

    const val GET_POPULAR_MOVIES = "http://api.themoviedb.org/3/discover/movie?language=zh&sort_by=popularity.desc&api_key=" + API_KEY

}
