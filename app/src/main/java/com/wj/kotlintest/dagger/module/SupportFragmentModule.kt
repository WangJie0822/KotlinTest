package com.wj.kotlintest.dagger.module

import android.support.v4.app.Fragment
import com.wj.kotlintest.dagger.sub.fragment.MoviesHighestRatedFragmentSub
import com.wj.kotlintest.fragment.MoviesHighestRatedFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 *
 *
 * @author 王杰
 */
@Module(subcomponents = arrayOf(MoviesHighestRatedFragmentSub::class))
abstract class SupportFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(MoviesHighestRatedFragment::class)
    abstract fun bindMoviesHighestRatedFragment(builder: MoviesHighestRatedFragmentSub.Builder): AndroidInjector.Factory<out Fragment>
}