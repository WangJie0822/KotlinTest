package com.wj.kotlintest.net

import android.util.Log
import com.wj.kotlintest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 公共参数添加拦截器
 */
class ParamsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (BuildConfig.DEBUG) {
            // 日志拦截
            Log.d("NET_INTERCEPTOR", "---------->> Intercept to add parameters <<----------")
        }

        // 获取请求信息
        val oldRequest = chain.request()

        // 添加新的参数
        val url = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("version", BuildConfig.VERSION_NAME)         // 版本名
                .addQueryParameter("platform", "android")                // 应用平台
                .addQueryParameter("imei", "")                           // 手机IMEI
                .build()

        // 生成新的请求
        val request = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(url)
                .build()

        return chain.proceed(request)
    }
}