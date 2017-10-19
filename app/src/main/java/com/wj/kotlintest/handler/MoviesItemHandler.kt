package com.wj.kotlintest.handler

import com.wj.kotlintest.bean.MoviesBean

/**
 * 电影列表事件处理
 */
interface MoviesItemHandler {

    fun onMoviesItemClick(item: MoviesBean)
}
