package com.wj.kotlintest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wj.kotlintest.base.BaseSwipeBackActivity
import com.wj.kotlintest.base.BlankPresenter
import com.wj.kotlintest.databinding.ActivityFavoriteBinding

/**
 * 最喜欢的电影列表界面
 */
class FavoriteActivity : BaseSwipeBackActivity<BlankPresenter, ActivityFavoriteBinding>() {

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
