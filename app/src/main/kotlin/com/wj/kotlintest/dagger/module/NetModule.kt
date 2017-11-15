package com.wj.kotlintest.dagger.module

import com.wj.kotlintest.BuildConfig
import com.wj.kotlintest.net.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * 网络模块依赖注入
 */
@Module
class NetModule {

    @Provides
    @Singleton
    fun netClient(): NetApi {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(ParamsInterceptor(BuildConfig.DEBUG))
                .addInterceptor(LogInterceptor(if(BuildConfig.DEBUG) Level.BODY else Level.NONE))
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(UrlDefinition.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit.create(NetApi::class.java)
    }
}