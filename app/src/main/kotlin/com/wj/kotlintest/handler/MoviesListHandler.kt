package com.wj.kotlintest.handler

import com.wj.kotlintest.entity.MoviesEntity

/**
 * 电影列表事件处理
 */
interface MoviesListHandler {
    /**
     * 电影列表条目点击事件
     *
     * @param entity 条目对应数据对象
     */
    fun onMoviesItemClick(entity: MoviesEntity)
}