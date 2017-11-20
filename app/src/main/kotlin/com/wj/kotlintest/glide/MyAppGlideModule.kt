package com.wj.kotlintest.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

/**
 * Glide Module
 *
 * @author 王杰
 */
@GlideModule
class MyAppGlideModule : AppGlideModule(){

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        builder?.setMemoryCache(LruResourceCache(10 * 1024 * 1024))
    }

    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
//        registry?.replace(GlideUrl::class.java, InputStream::class.java, Okhttp)
    }

    override fun isManifestParsingEnabled() = false
}