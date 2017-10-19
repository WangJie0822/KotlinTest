package com.wj.kotlintest.glide

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 *
 *
 * @author 王杰
 */
@GlideModule
class MyAppGlideModule : AppGlideModule(){

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}