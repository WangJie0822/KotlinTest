package com.wj.kotlintest.fragment

import android.support.v7.widget.GridLayoutManager
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.MoviesListAdapter
import com.wj.kotlintest.base.BaseFragment
import com.wj.kotlintest.databinding.FragmentMoviesListBinding
import com.wj.kotlintest.entity.MoviesEntity
import com.wj.kotlintest.entity.MoviesListEntity
import com.wj.kotlintest.handler.MoviesItemHandler
import com.wj.kotlintest.mvp.MoviesListPresenter
import com.wj.kotlintest.mvp.MoviesListView
import com.wj.kotlintest.utils.ToastUtil
import javax.inject.Inject

/**
 * 电影列表界面
 */
class MoviesListFragment : BaseFragment<MoviesListPresenter, FragmentMoviesListBinding>(), MoviesListView {

    private val mData = ArrayList<MoviesEntity>()

    @Inject
    lateinit var adapter: MoviesListAdapter

    override fun layoutResID(): Int {
        return R.layout.fragment_movies_list
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
        override fun onMoviesItemClick(item: MoviesEntity) {
            ToastUtil.show(item.title)
        }
    }
}