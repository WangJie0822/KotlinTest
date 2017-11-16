package com.wj.kotlintest.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.wj.kotlintest.R
import com.wj.kotlintest.activity.MoviesDetailsActivity
import com.wj.kotlintest.adapter.MoviesListAdapter
import com.wj.kotlintest.base.BaseFragment
import com.wj.kotlintest.databinding.FragmentMoviesListBinding
import com.wj.kotlintest.entity.MoviesEntity
import com.wj.kotlintest.entity.MoviesListEntity
import com.wj.kotlintest.flag.TYPE_HIGHEST_RATE
import com.wj.kotlintest.flag.TYPE_POPULAR
import com.wj.kotlintest.handler.MoviesItemHandler
import com.wj.kotlintest.mvp.MoviesListPresenter
import com.wj.kotlintest.mvp.MoviesListView
import com.wj.kotlintest.utils.ToastUtil
import com.wj.swipetoloadlayout.OnRefreshListener
import javax.inject.Inject

/**
 * 电影列表界面
 */
class MoviesListFragment : BaseFragment<MoviesListPresenter, FragmentMoviesListBinding>(), MoviesListView {

    private val mData = ArrayList<MoviesEntity>()

    @Inject
    lateinit var adapter: MoviesListAdapter

    companion object {
        /**
         * 创建对应 Fragment 对象
         *
         * @param type 参数，列表类型 [TYPE_HIGHEST_RATE]、[TYPE_POPULAR]
         */
        fun actionCreate(type: Int): MoviesListFragment {
            val frag = MoviesListFragment()
            val args = Bundle()
            args.putInt("MOVIES_TYPE", type)
            frag.arguments = args
            return frag
        }
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_movies_list
    }

    override fun initView() {

        presenter.attach(this)

        adapter.bindData(mData)
        adapter.bindHandler(HighestRatedHandler())

        mBinding.swipeTarget.layoutManager = GridLayoutManager(mContext, 2)
        mBinding.swipeTarget.adapter = adapter

        fun initData() {
            when (arguments.getInt("MOVIES_TYPE")) {
                TYPE_HIGHEST_RATE -> presenter.getHighestRatedMovies()
                TYPE_POPULAR -> presenter.getPopularMovies()
                else -> presenter.getHighestRatedMovies()
            }
        }

        mBinding.swipe.refreshListener = object : OnRefreshListener {
            override fun onRefresh() {
                initData()
            }
        }

        onLoading()
        initData()
    }

    override fun initTitleBar() {
        showTitle()
        setTitleStr(when (arguments.getInt("MOVIES_TYPE")) {
            TYPE_HIGHEST_RATE -> "高评分电影"
            TYPE_POPULAR -> "最流行电影"
            else -> ""
        })
    }

    override fun onListComplete() {
        mBinding.swipe.onComplete()
    }

    override fun notifyData(data: MoviesListEntity) {
        mData.clear()
        mData.addAll(data.results)
        adapter.notifyDataSetChanged()
    }

    inner class HighestRatedHandler : MoviesItemHandler {
        override fun onMoviesItemClick(item: MoviesEntity) {
            item.title?.let { ToastUtil.show(it) }
            MoviesDetailsActivity.actionStart(mContext)
        }
    }
}