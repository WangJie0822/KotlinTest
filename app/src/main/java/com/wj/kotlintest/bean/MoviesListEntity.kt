package com.wj.kotlintest.bean

import com.wj.kotlintest.base.BaseEntity
import java.util.*

/**
 * @author 王杰
 */
class MoviesListEntity : BaseEntity() {

    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    lateinit var results: ArrayList<MoviesBean>

}
