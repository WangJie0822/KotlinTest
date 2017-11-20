package com.wj.kotlintest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.FragVpAdapter
import com.wj.kotlintest.base.BaseActivity
import com.wj.kotlintest.base.BlankPresenter
import com.wj.kotlintest.constants.MOVIES_TYPE_HIGHEST_RATE
import com.wj.kotlintest.constants.MOVIES_TYPE_POPULAR
import com.wj.kotlintest.databinding.ActivityMainBinding
import com.wj.kotlintest.fragment.MoviesListFragment

/**
 * 主界面
 */
class MainActivity : BaseActivity<BlankPresenter, ActivityMainBinding>() {

    companion object {
        /**
         * 界面入口
         *
         * @param context Context 对象
         */
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 添加 电影列表 Fragment 到集合
        val mFrags = ArrayList<Fragment>()
        mFrags.add(MoviesListFragment.actionCreate(MOVIES_TYPE_HIGHEST_RATE))
        mFrags.add(MoviesListFragment.actionCreate(MOVIES_TYPE_POPULAR))

        // 设置适配器
        mBinding.vp.adapter = FragVpAdapter.Builder()
                .manager(supportFragmentManager)
                .frags(mFrags)
                .build()

    }

    override fun initTitleBar() {
    }

}
