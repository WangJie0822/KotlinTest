package com.wj.kotlintest.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.FragVpAdapter
import com.wj.kotlintest.base.BaseActivity
import com.wj.kotlintest.base.BlankPresenter
import com.wj.kotlintest.databinding.ActivityMainBinding
import com.wj.kotlintest.flag.TYPE_HIGHEST_RATE
import com.wj.kotlintest.flag.TYPE_POPULAR
import com.wj.kotlintest.fragment.MoviesListFragment

/**
 * 主界面
 */
class MainActivity : BaseActivity<BlankPresenter, ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFrags = ArrayList<Fragment>()
        mFrags.add(MoviesListFragment.actionCreate(TYPE_HIGHEST_RATE))
        mFrags.add(MoviesListFragment.actionCreate(TYPE_POPULAR))

        mBinding.vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> setTitleStr("高评分电影")
                    1 -> setTitleStr("最流行电影")
                }
            }
        })

        mBinding.vp.adapter = FragVpAdapter.Builder()
                .manager(supportFragmentManager)
                .frags(mFrags)
                .build()

    }

    override fun initTitleBar() {
        showTitle()
        setTitleStr("高评分电影")
    }

}
