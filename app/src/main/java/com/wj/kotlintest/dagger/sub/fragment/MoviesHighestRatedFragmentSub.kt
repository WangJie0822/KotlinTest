package com.wj.kotlintest.dagger.sub.fragment

import com.wj.kotlintest.fragment.MoviesHighestRatedFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 *
 *
 * @author 王杰
 */
@Subcomponent
interface MoviesHighestRatedFragmentSub : AndroidInjector<MoviesHighestRatedFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MoviesHighestRatedFragment>() {}
}