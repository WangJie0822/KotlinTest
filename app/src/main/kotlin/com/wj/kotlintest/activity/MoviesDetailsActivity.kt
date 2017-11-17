package com.wj.kotlintest.activity

import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.wj.kotlintest.BR
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.ReviewsAdapter
import com.wj.kotlintest.adapter.TrailersAdapter
import com.wj.kotlintest.base.BaseSwipeBackActivity
import com.wj.kotlintest.constants.FAVORITE_MOVIES
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

    @Inject
    lateinit var trailersAdapter: TrailersAdapter
    @Inject
    lateinit var reviewsAdapter: ReviewsAdapter

    companion object {
        fun actionStart(context: Context, movies: MoviesEntity) {
            val intent = Intent(context, MoviesDetailsActivity::class.java)
            intent.putExtra("MOVIES", movies)
            context.startActivity(intent)
        }
    }

    private val movies: MoviesEntity
        get() = intent.getParcelableExtra("MOVIES")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)

        presenter.attach(this)

        mBinding.handler = MoviesDetailsHandler()

        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mBinding.collapsingToolbar.title = movies.title
        GlideApp.with(mContext)
                .load(UrlDefinition.POSTER_PATH + movies.backdrop_path)
                .into(mBinding.ivHeader)
        mBinding.handler?.releaseDate = movies.release_date
        mBinding.handler?.rating = movies.vote_average
        mBinding.handler?.description = movies.overview

        mBinding.handler?.favorite = SharedPrefUtil.getBoolean(FAVORITE_MOVIES + movies.id, false)

        presenter.getTrailers()
        presenter.getReviews()
    }

    override fun getMoviesId() = movies.id.toString()

    override fun notifyTrailers(entity: TrailersEntity) {

        if (entity.results.isEmpty()) {
            return
        }

        mBinding.handler?.hasTrailers = true

        trailersAdapter.data = entity.results
        trailersAdapter.handler = mBinding.handler

        mBinding.rvTrailers.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvTrailers.adapter = trailersAdapter
    }

    override fun notifyReviews(entity: ReviewsEntity) {

        if (entity.results.isEmpty()) {
            return
        }

        mBinding.handler?.hasReviews = true

        reviewsAdapter.data = entity.results
        reviewsAdapter.handler = mBinding.handler

        mBinding.rvReviews.layoutManager = LinearLayoutManager(mContext)
        mBinding.rvReviews.adapter = reviewsAdapter
    }

    inner class MoviesDetailsHandler : BaseObservable() {

        @get:Bindable
        var favorite = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.favorite)
            }


        var releaseDate: String? = null
            @Bindable get() = String.format(getString(R.string.release_date), field)
            set(value) {
                field = value
                notifyPropertyChanged(BR.releaseDate)
            }

        var rating: String? = null
            @Bindable get() = String.format(getString(R.string.rating), field)
            set(value) {
                field = value
                notifyPropertyChanged(BR.rating)
            }

        @get:Bindable
        var description: String? = null
            set(value) {
                field = value
                notifyPropertyChanged(BR.description)
            }

        @get:Bindable
        var hasTrailers = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.hasTrailers)
            }

        @get:Bindable
        var hasReviews = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.hasReviews)
            }

        fun onLongTextClick(tv: View) {
            if (tv is TextView) {
                if (tv.maxLines == 5) {
                    tv.maxLines = 500
                } else {
                    tv.maxLines = 5
                }
            }
        }

        fun onTrailersClick(entity: TrailersEntity.Result) {
            val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(entity.videoUrl()))
            startActivity(playVideoIntent)
        }

        fun onFavoriteClick() {
            favorite = !favorite
            SharedPrefUtil.putBoolean(FAVORITE_MOVIES + movies.id, favorite)
        }
    }

}
