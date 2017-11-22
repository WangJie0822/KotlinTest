package com.wj.kotlintest.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.wj.kotlintest.R
import com.wj.kotlintest.activity.MoviesDetailsActivity
import com.wj.kotlintest.adapter.MoviesListAdapter
import com.wj.kotlintest.base.BaseFragment
import com.wj.kotlintest.constants.MOVIES_LIST_TYPE
import com.wj.kotlintest.constants.MOVIES_TYPE_HIGHEST_RATE
import com.wj.kotlintest.constants.MOVIES_TYPE_POPULAR
import com.wj.kotlintest.databinding.FragmentMoviesListBinding
import com.wj.kotlintest.entity.MoviesEntity
import com.wj.kotlintest.entity.MoviesListEntity
import com.wj.kotlintest.handler.MoviesListHandler
import com.wj.kotlintest.mvp.MoviesListPresenter
import com.wj.kotlintest.mvp.MoviesListView
import com.wj.swipelayout.OnRefreshListener
import javax.inject.Inject

/**
 * 电影列表界面
 */
class MoviesListFragment : BaseFragment<MoviesListPresenter, FragmentMoviesListBinding>(), MoviesListView {

    /** 电影列表适配器 */
    @Inject
    lateinit var adapter: MoviesListAdapter

    /** 标记-电影列表类型 */
    private val moviesType
        get() = arguments.getInt(MOVIES_LIST_TYPE)

    companion object {
        /**
         * 创建对应 Fragment 对象
         *
         * @param type 参数，列表类型 [MOVIES_TYPE_HIGHEST_RATE]、[MOVIES_TYPE_POPULAR]
         */
        fun actionCreate(type: Int): MoviesListFragment {
            val frag = MoviesListFragment()
            val args = Bundle()
            args.putInt(MOVIES_LIST_TYPE, type)
            frag.arguments = args
            return frag
        }
    }

    override fun layoutResID() = R.layout.fragment_movies_list

    override fun initView() {

        // 绑定 MVP
        presenter.attach(this)

        // 绑定数据、Handler
        adapter.data = arrayListOf()
        adapter.handler = MoviesListActivityHandler()

        // 设置 RecyclerView 布局管理、适配器
        mBinding.swipeTarget.layoutManager = GridLayoutManager(mContext, 2)
        mBinding.swipeTarget.adapter = adapter

        /**
         * 初始化数据
         */
        fun initData() {
            // 根据
            when (moviesType) {
                MOVIES_TYPE_HIGHEST_RATE -> presenter.getHighestRatedMovies()
                MOVIES_TYPE_POPULAR -> presenter.getPopularMovies()
                else -> presenter.getHighestRatedMovies()
            }
        }

        // 设置下拉刷新监听
        mBinding.swipe.refreshListener = object : OnRefreshListener {
            override fun onRefresh() {
                // 初始化数据
                initData()
            }
        }

        // 显示加载中界面
        loading()
        // 加载数据
        initData()
    }

    override fun initTitleBar() {
        // 显示标题栏
        showTitle()
        // 根据列表类型，设置标题文本
        setTitleStr(when (moviesType) {
            MOVIES_TYPE_HIGHEST_RATE -> "高评分电影"
            MOVIES_TYPE_POPULAR -> "最流行电影"
            else -> ""
        })
    }

    override fun onListComplete() {
        mBinding.swipe.onComplete()
    }

    override fun notifyData(data: MoviesListEntity) {
        // 加载完成，更新界面
        adapter.data.addAll(data.results)
        adapter.notifyDataSetChanged()
    }

    /**
     * 电影列表界面事件处理类
     */
    inner class MoviesListActivityHandler: MoviesListHandler {

        /**
         * 电影列表条目点击事件
         *
         * @param entity 条目对应数据对象
         */
        override fun onMoviesItemClick(entity: MoviesEntity) {
            MoviesDetailsActivity.actionStart(mContext, entity)
        }
    }
}