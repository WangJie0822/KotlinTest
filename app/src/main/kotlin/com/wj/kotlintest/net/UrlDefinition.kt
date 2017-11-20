package com.wj.kotlintest.net

import com.wj.kotlintest.BuildConfig

/**
 * 网络接口地址
 */
object UrlDefinition {

    /** 正式版本服务器地址 */
    private val API_RELEASE = "www.baidu.com"
    /** 测试版本服务器地址 */
    private val API_DEBUG = "www.baidu.com"
    /** 实际使用服务器地址 */
    private val API_DOMAIN = if (BuildConfig.DEBUG) API_DEBUG else API_RELEASE
    /** 标记-是否使用加密协议 */
    private val USE_SSL = false
    /** 请求方案 */
    private val SCHEME = if (USE_SSL) "https://" else "http://"
    /** 应用 API 密匙 */
    private const val API_KEY = BuildConfig.API_KEY
    /** 实际使用完整服务器地址 */
    val BASE_URL = SCHEME + API_DOMAIN

    /** 图片服务器地址 */
    val POSTER_PATH = "http://image.tmdb.org/t/p/w342"
    /** 高评分电影列表地址 */
    const val GET_HIGHEST_RATED_MOVIES = "http://api.themoviedb.org/3/discover/movie?vote_count.gte=500&language=zh&sort_by=vote_average.desc&api_key=" + API_KEY
    /** 最流行电影列表地址 */
    const val GET_POPULAR_MOVIES = "http://api.themoviedb.org/3/discover/movie?language=zh&sort_by=popularity.desc&api_key=" + API_KEY
    /** 特别收录列表地址 */
    const val GET_TRAILERS = "http://api.themoviedb.org/3/movie/%s/videos?api_key=" + API_KEY
    /** 评论列表地址 */
    const val GET_REVIEWS = "http://api.themoviedb.org/3/movie/%s/reviews?api_key=" + API_KEY
    /** YouTube 视频地址 */
    const val YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%1\$s"
    /** YouTube 视频缩略图地址 */
    const val YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1\$s/0.jpg"

}
