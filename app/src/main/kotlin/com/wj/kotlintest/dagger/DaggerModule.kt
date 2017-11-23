package com.wj.kotlintest.dagger

import com.orhanobut.logger.Logger
import com.wj.kotlintest.BuildConfig
import com.wj.kotlintest.activity.FavoriteActivity
import com.wj.kotlintest.activity.MainActivity
import com.wj.kotlintest.activity.MoviesDetailsActivity
import com.wj.kotlintest.activity.SplashActivity
import com.wj.kotlintest.fragment.MoviesListFragment
import com.wj.kotlintest.net.*
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Activity Dagger2 Module
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMoviesDetailsActivity(): MoviesDetailsActivity

    @ContributesAndroidInjector
    abstract fun contributeFavoriteActivity(): FavoriteActivity
}

/**
 * SupportFragment Dagger2 Module
 */
@Module
abstract class SupportFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMoviesListFragment(): MoviesListFragment
}

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
                .addInterceptor(LogInterceptor(
                        if (BuildConfig.DEBUG) Level.BODY else Level.NONE,
                        object : InterceptorLogger {
                            override fun log(msg: String) {
                                Logger.d(msg)
                            }
                        }))
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