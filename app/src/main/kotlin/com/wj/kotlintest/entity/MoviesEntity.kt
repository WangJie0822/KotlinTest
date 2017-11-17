package com.wj.kotlintest.entity

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * @author 王杰
 */
class MoviesEntity() : Parcelable {
    /**
     * poster_path : /9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg
     * adult : false
     * overview : 1947年，小有成就的青年银行家安迪（蒂姆·罗宾斯 Tim Robbins 饰）因涉嫌杀害妻子及她的情人而锒铛入狱。在这座名为肖申克的监狱内，希望似乎虚无缥缈，终身监禁的惩罚无疑注定了安迪接下来灰暗绝望的人生。未过多久，安迪尝试接近囚犯中颇有声望的瑞德（摩根·弗里曼 Morgan Freeman 饰），请求对方帮自己搞来小锤子。以此为契机，二人逐渐熟络，安迪也仿佛在鱼龙混杂、罪恶横生、黑白混淆的牢狱中找到属于自己的求生之道。他利用自身的专业知识，帮助监狱管理层逃税、洗黑钱，同时凭借与瑞德的交往在犯人中间也渐渐受到礼遇。表面看来，他已如瑞德那样对那堵高墙从憎恨转变为处之泰然，但是对自由的渴望仍促使他朝着心中的希望和目标前进。而关于其罪行的真相，似乎更使这一切朝前推进了一步……
     * release_date : 1994-09-23
     * genre_ids : [18,80]
     * id : 278
     * original_title : The Shawshank Redemption
     * original_language : en
     * title : 肖申克的救赎
     * backdrop_path : /xBKGJQsAIeweesB79KC89FpBrVr.jpg
     * popularity : 10.718322
     * vote_count : 6557
     * video : false
     * vote_average : 8.4
     */

    var poster_path: String? = null
    var isAdult: Boolean = false
    var overview: String? = null
    var release_date: String? = null
    var id: Int = 0
    var original_title: String? = null
    var original_language: String? = null
    var title: String? = null
    var backdrop_path: String? = null
    var popularity: Double = 0.toDouble()
    var vote_count: Int = 0
    var isVideo: Boolean = false
    var vote_average: String? = null
    var genre_ids: ArrayList<Int>? = null

    constructor(parcel: Parcel) : this() {
        poster_path = parcel.readString()
        isAdult = parcel.readByte() != 0.toByte()
        overview = parcel.readString()
        release_date = parcel.readString()
        id = parcel.readInt()
        original_title = parcel.readString()
        original_language = parcel.readString()
        title = parcel.readString()
        backdrop_path = parcel.readString()
        popularity = parcel.readDouble()
        vote_count = parcel.readInt()
        isVideo = parcel.readByte() != 0.toByte()
        vote_average = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(poster_path)
        parcel.writeByte(if (isAdult) 1 else 0)
        parcel.writeString(overview)
        parcel.writeString(release_date)
        parcel.writeInt(id)
        parcel.writeString(original_title)
        parcel.writeString(original_language)
        parcel.writeString(title)
        parcel.writeString(backdrop_path)
        parcel.writeDouble(popularity)
        parcel.writeInt(vote_count)
        parcel.writeByte(if (isVideo) 1 else 0)
        parcel.writeString(vote_average)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoviesEntity> {
        override fun createFromParcel(parcel: Parcel): MoviesEntity {
            return MoviesEntity(parcel)
        }

        override fun newArray(size: Int): Array<MoviesEntity?> {
            return arrayOfNulls(size)
        }
    }

}
