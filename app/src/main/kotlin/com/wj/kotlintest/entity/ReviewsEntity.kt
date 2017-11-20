package com.wj.kotlintest.entity

import com.google.gson.annotations.SerializedName
import com.wj.kotlintest.base.BaseEntity

/**
 * 评论列表实体类
 */
data class ReviewsEntity(
        @SerializedName("id") val id: Int, //278
        @SerializedName("page") val page: Int, //1
        @SerializedName("results") val results: ArrayList<Result>,
        @SerializedName("total_pages") val totalPages: Int, //1
        @SerializedName("total_results") val totalResults: Int //3
) : BaseEntity() {
    /**
     * 评论信息实体类
     */
    data class Result(
            @SerializedName("id") val id: String, //5723a329c3a3682e720005db
            @SerializedName("author") val author: String, //elshaarawy
            @SerializedName("content") val content: String, //very good movie 9.5/10 محمد الشعراوى
            @SerializedName("url") val url: String //https://www.themoviedb.org/review/5723a329c3a3682e720005db
    )
}
