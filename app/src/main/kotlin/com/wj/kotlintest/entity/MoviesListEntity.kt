package com.wj.kotlintest.entity

import com.wj.kotlintest.base.BaseEntity
import java.util.*

/**
 * 电影列表实体类
 */
class MoviesListEntity : BaseEntity() {

    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    lateinit var results: ArrayList<MoviesEntity>

}
