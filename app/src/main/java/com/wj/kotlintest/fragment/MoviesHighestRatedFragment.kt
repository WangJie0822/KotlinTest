package com.wj.kotlintest.fragment

import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.MoviesListAdapter
import com.wj.kotlintest.base.BaseFragment
import com.wj.kotlintest.bean.MoviesBean
import com.wj.kotlintest.bean.MoviesListEntity
import com.wj.kotlintest.databinding.FragmentMoviesHighestRatedBinding
import com.wj.kotlintest.handler.MoviesItemHandler
import com.wj.kotlintest.mvp.MoviesHighestRatedPresenter
import com.wj.kotlintest.mvp.MoviesHighestRatedView
import javax.inject.Inject

/**
 * 评价最高的电影
 */
class MoviesHighestRatedFragment : BaseFragment<MoviesHighestRatedPresenter, FragmentMoviesHighestRatedBinding>(), MoviesHighestRatedView {

    val mData = ArrayList<MoviesBean>()

    @Inject
    lateinit var adapter: MoviesListAdapter

    override fun layoutResID(): Int {
        return R.layout.fragment_movies_highest_rated
    }

    override fun initView() {

        presenter.attach(this)

        adapter.bindData(mData)
        adapter.bindHandler(HighestRatedHandler())

        mBinding.rv.layoutManager = GridLayoutManager(mContext, 2)
        mBinding.rv.adapter = adapter

        presenter.getHighestRatedMovies()
    }

    override fun initTitleBar() {
    }

    override fun notifyData(data: MoviesListEntity) {
        mData.clear()
        mData.addAll(data.results)
        adapter.notifyDataSetChanged()
    }

    inner class HighestRatedHandler : MoviesItemHandler {
        override fun onMoviesItemClick(item: MoviesBean) {
            Toast.makeText(mContext, item.original_title, Toast.LENGTH_SHORT).show()
        }

    }
}