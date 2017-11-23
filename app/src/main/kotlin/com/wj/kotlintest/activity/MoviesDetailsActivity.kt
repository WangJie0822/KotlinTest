package com.wj.kotlintest.activity

import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.wj.kotlintest.BR
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.ReviewsAdapter
import com.wj.kotlintest.adapter.TrailersAdapter
import com.wj.kotlintest.base.BaseSwipeBackActivity
import com.wj.kotlintest.constants.FAVORITE_KEY
import com.wj.kotlintest.databinding.ActivityMoviesDetailsBinding
import com.wj.kotlintest.entity.MoviesEntity
import com.wj.kotlintest.entity.ReviewsEntity
import com.wj.kotlintest.entity.TrailersEntity
import com.wj.kotlintest.glide.GlideApp
import com.wj.kotlintest.mvp.MoviesDetailsPresenter
import com.wj.kotlintest.mvp.MoviesDetailsView
import com.wj.kotlintest.net.UrlDefinition
import com.wj.kotlintest.utils.SharedPrefUtil
import javax.inject.Inject

/**
 * 电影详情
 */
class MoviesDetailsActivity
    : BaseSwipeBackActivity<MoviesDetailsPresenter, ActivityMoviesDetailsBinding>(),
        MoviesDetailsView {

    /** 特别收录适配器 */
    @Inject
    lateinit var trailersAdapter: TrailersAdapter
    /** 评论适配器 */
    @Inject
    lateinit var reviewsAdapter: ReviewsAdapter

    /** 从上层 Activity 传递过来的电影实体对象 */
    private val movies: MoviesEntity
        get() = intent.getParcelableExtra("MOVIES")

    companion object {
        /**
         * 界面入口
         *
         * @param context Context 对象
         * @param movies 电影s实体类对象 [MoviesEntity]
         */
        fun actionStart(context: Context, movies: MoviesEntity) {
            val intent = Intent(context, MoviesDetailsActivity::class.java)
            intent.putExtra("MOVIES", movies)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)

        // 绑定 MVP
        presenter.attach(this)

        // 绑定 Handler
        mBinding.handler = MoviesDetailsHandler()

        // 设置 Toolbar
        setSupportActionBar(mBinding.toolbar)
        // 显示返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 显示页面数据
        mBinding.collapsingToolbar.title = movies.title
        GlideApp.with(mContext)
                .load(UrlDefinition.POSTER_PATH + movies.backdrop_path)
                .into(mBinding.ivHeader)
        mBinding.handler?.releaseDate = movies.release_date
        mBinding.handler?.rating = movies.vote_average
        mBinding.handler?.description = movies.overview

        // 获取保存的收藏状态
        mBinding.handler?.favorite = "" != SharedPrefUtil.getString(FAVORITE_KEY + "_" + movies.id, "")

        // 获取特别收录信息
        presenter.getTrailers()
        // 获取评论信息
        presenter.getReviews()
    }

    override fun getMoviesId() = movies.id.toString()

    override fun notifyTrailers(entity: TrailersEntity) {

        if (entity.results.isEmpty()) {
            // 没有特别收录信息，返回不做操作
            return
        }

        // 显示特别收录
        mBinding.handler?.hasTrailers = true

        // 绑定数据、Handler
        trailersAdapter.data = entity.results
        trailersAdapter.handler = mBinding.handler

        // 设置 RecyclerView 为横向，设置适配器
        mBinding.rvTrailers.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvTrailers.adapter = trailersAdapter
    }

    override fun notifyReviews(entity: ReviewsEntity) {

        if (entity.results.isEmpty()) {
            // 没有评论信息，返回不做操作
            return
        }

        // 显示评论
        mBinding.handler?.hasReviews = true

        // 绑定数据、Handler
        reviewsAdapter.data = entity.results
        reviewsAdapter.handler = mBinding.handler

        // 设置 RecyclerView 竖向，设置适配器
        mBinding.rvReviews.layoutManager = LinearLayoutManager(mContext)
        mBinding.rvReviews.adapter = reviewsAdapter
    }

    /**
     * 电影详情界面数据绑定、事件处理类
     */
    inner class MoviesDetailsHandler : BaseObservable() {

        /** 标记-电影是否收藏 */
        @get:Bindable
        var favorite = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.favorite)
            }

        /** 电影上映时间 */
        var releaseDate: String? = null
            @Bindable get() = String.format(getString(R.string.release_date), field)
            set(value) {
                field = value
                notifyPropertyChanged(BR.releaseDate)
            }

        /** 电影评分 */
        var rating: String? = null
            @Bindable get() = String.format(getString(R.string.rating), field)
            set(value) {
                field = value
                notifyPropertyChanged(BR.rating)
            }

        /** 电影概要 */
        @get:Bindable
        var description: String? = null
            set(value) {
                field = value
                notifyPropertyChanged(BR.description)
            }

        /** 标记-是否有特别收录 */
        @get:Bindable
        var hasTrailers = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.hasTrailers)
            }

        /** 标记-是否有评论 */
        @get:Bindable
        var hasReviews = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.hasReviews)
            }

        /**
         * 长文本点击事件处理。
         * TextView 默认最多显示5行，点击展开、折叠
         *
         * @param tv TextView 对象
         */
        fun onLongTextClick(tv: View) {
            if (tv is TextView) {
                // 是 TextView
                if (tv.maxLines == 5) {
                    // 最多显示5行，设置最多显示500行，展开
                    tv.maxLines = 500
                } else {
                    // 设置最多显示5行，折叠
                    tv.maxLines = 5
                }
            }
        }

        /**
         * 特别收录条目点击事件。
         * 点击跳转视频界面
         *
         * @param entity 特别收录条目数据
         */
        fun onTrailersClick(entity: TrailersEntity.Result) {
            // 设置 Intent 对象
            val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(entity.videoUrl()))
            // 开启 Activity
            startActivity(playVideoIntent)
        }

        /**
         * 收藏点击事件处理。
         *
         * 点击加入、取消收藏
         *
         * @param v [android.support.design.widget.FloatingActionButton] 对象
         */
        fun onFavoriteClick(v: View) {
            // 根据是否收藏获取提示文本
            val str = if (favorite) {
                // 收藏状态保存到 SharedPref
                SharedPrefUtil.putString(FAVORITE_KEY + "_" + movies.id, "")
                "取消收藏"
            } else {
                // 收藏状态保存到 SharedPref
                SharedPrefUtil.putString(FAVORITE_KEY + "_" + movies.id, Gson().toJson(movies))
                "加入收藏"
            }
            // 是否收藏标记取反
            favorite = !favorite
            // 创建 Snackbar 对象
            val snackbar = Snackbar.make(v, str, Snackbar.LENGTH_SHORT)
            // 设置 Snackbar 背景色为主题色
            @Suppress("DEPRECATION")
            snackbar.view.setBackgroundColor(resources.getColor(R.color.colorTheme))
            // 显示 Snackbar
            snackbar.show()
        }

        /**
         * 收藏长按事件处理
         */
        fun onFavoriteLongClick() {
            // 跳转最喜欢的电影列表
            FavoriteActivity.actionStart(mContext)
        }
    }

}
