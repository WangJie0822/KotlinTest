package com.wj.kotlintest.handler

import com.wj.kotlintest.entity.MoviesEntity

/**
 * 电影列表事件处理
 */
interface MoviesItemHandler {
    fun onMoviesItemClick(item: MoviesEntity)
}
