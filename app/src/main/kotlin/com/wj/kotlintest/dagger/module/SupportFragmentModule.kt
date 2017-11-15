package com.wj.kotlintest.dagger.module

import com.wj.kotlintest.fragment.MoviesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *
 *
 * @author 王杰
 */
@Module
abstract class SupportFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMoviesListFragment():MoviesListFragment
}