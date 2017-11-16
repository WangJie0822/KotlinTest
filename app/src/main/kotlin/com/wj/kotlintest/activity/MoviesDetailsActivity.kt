package com.wj.kotlintest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wj.kotlintest.R
import com.wj.kotlintest.base.BaseActivity
import com.wj.kotlintest.base.BlankPresenter
import com.wj.kotlintest.databinding.ActivityMoviesDetailsBinding

class MoviesDetailsActivity : BaseActivity<BlankPresenter, ActivityMoviesDetailsBinding>() {
    override fun initTitleBar() {
        showTitle()
        setTitleStr("details")
        setIvLeft()
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MoviesDetailsActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)
    }
}
