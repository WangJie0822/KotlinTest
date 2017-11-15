package com.wj.kotlintest.expanding

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.regex.Pattern

/**
 * 对 json 字符串进行格式化
 *
 * @return json 格式化完成字符串
 */
fun String.jsonFormat(): String {
    var format: String
    try {
        val trimJson = this.trim { it <= ' ' }
        format = when {
            trimJson.startsWith("{") -> {
                val jsonObject = JSONObject(trimJson)
                jsonObject.toString(2)
            }
            trimJson.startsWith("[") -> {
                val jsonArray = JSONArray(trimJson)
                jsonArray.toString(2)
            }
            else -> "Invalid Json"
        }
    } catch (e: JSONException) {
        Log.e("NET_INTERCEPTOR", "Json Format Error", e)
        format = "Invalid Json"
    }
    return format
}

/**
 * 检测字符串是否满足 Email 格式
 *
 * @return 字符串是否是 Email
 */
fun String.isEmail(): Boolean {
    val regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * 检测字符串是否满足手机号格式
 *
 * @return 字符串是否是手机号码
 */
fun String.isPhone(): Boolean {
    val regex = "^[1][4,3,5,7,8]+\\d{9}"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * 判断字符串中是否包含 Emoji
 *
 * @return 是否包含 Emoji
 */
fun String.containsEmoji(): Boolean {
    return (0 until this.length)
            .map { this[it].toInt() }
            .none {
                (it == 0x0) || (it == 0x9) || (it == 0xA) || (it == 0xD)
                        || (it in 0x20..0xD7FF)
                        || (it in 0xE000..0xFFFD)
                        || (it in 0x10000..0x10FFFF)
            }
}