package com.wj.kotlintest.entity

import com.google.gson.annotations.SerializedName
import com.wj.kotlintest.base.BaseEntity
import com.wj.kotlintest.net.UrlDefinition

/**
 * 特别收录列表实体类
 */
data class TrailersEntity(
        @SerializedName("id") val id: Int = 0, //278
        @SerializedName("results") val results: ArrayList<Result> = arrayListOf()
) : BaseEntity() {
    /**
     * 特别收录信息实体类
     */
    data class Result(
            @SerializedName("id") val id: String = "", //533ec653c3a3685448000249
            @SerializedName("iso_639_1") val iso6391: String = "", //en
            @SerializedName("iso_3166_1") val iso31661: String = "", //US
            @SerializedName("key") val key: String = "", //K_tLp7T6U1c
            @SerializedName("name") val name: String = "", //Official Trailer
            @SerializedName("site") val site: String = "", //YouTube
            @SerializedName("size") val size: Int = 0, //480
            @SerializedName("type") val type: String = "" //Trailer
    ) {
        fun imgUrl() = String.format(UrlDefinition.YOUTUBE_THUMBNAIL_URL, id)
        fun videoUrl() = String.format(UrlDefinition.YOUTUBE_VIDEO_URL, key)
    }
}

