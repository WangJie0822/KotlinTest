package com.wj.kotlintest.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.wj.kotlintest.BlankPresenter
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.FragVpAdapter
import com.wj.kotlintest.base.BaseActivity
import com.wj.kotlintest.databinding.ActivityMainBinding
import com.wj.kotlintest.fragment.MoviesHighestRatedFragment

/**
 * 主界面
 */
class MainActivity : BaseActivity<BlankPresenter, ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mData = ArrayList<Fragment>()
        mData.add(MoviesHighestRatedFragment())

        mBinding.vp.adapter = FragVpAdapter.builder()
                .manager(supportFragmentManager)
                .frags(mData)
                .build()

    }

    override fun initTitleBar() {
        showTitle()
        setTitleStr(str = "高评分电影")
    }

}
