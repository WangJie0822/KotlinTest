package com.wj.kotlintest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wj.kotlintest.R
import com.wj.kotlintest.base.BaseSwipeBackActivity
import com.wj.kotlintest.databinding.ActivityFavoriteBinding
import com.wj.kotlintest.mvp.FavoritePresenter

/**
 * 最喜欢的电影列表
 */
class FavoriteActivity : BaseSwipeBackActivity<FavoritePresenter, ActivityFavoriteBinding>() {

    companion object {
        /**
         * 界面入口
         *
         * @param context Context 对象
         */
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, FavoriteActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
    }

    override fun initTitleBar() {
        showTitle()
        setTitleStr("最喜欢的电影")
    }
}
