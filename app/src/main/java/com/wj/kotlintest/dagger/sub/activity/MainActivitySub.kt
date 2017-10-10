package com.wj.kotlintest.dagger.sub.activity

import com.wj.kotlintest.activity.MainActivity

import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * MainActivity Dagger2 组件
 *
 * @author 王杰
 */
@Subcomponent
interface MainActivitySub : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
