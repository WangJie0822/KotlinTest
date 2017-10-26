package com.wj.kotlintest.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Fragment ViewPager 适配器类
 */
class FragVpAdapter private constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    /**
     * Fragment 集合
     */
    private lateinit var mFrag: ArrayList<Fragment>

    override fun getItem(position: Int): Fragment {
        return mFrag[position]
    }

    override fun getCount(): Int {
        return mFrag.size
    }

    /**
     * FragVpAdapter 建造者类
     */
    class Builder {

        /**
         * Fragment 集合
         */
        private lateinit var mFrag: ArrayList<Fragment>

        /**
         * Fragment 管理器
         */
        private lateinit var fm: FragmentManager

        /**
         * 绑定 Fragment 集合
         */
        fun frags(frags: ArrayList<Fragment>): Builder {
            mFrag = frags
            return this
        }

        /**
         * 绑定 Fragment 管理器
         */
        fun manager(fm: FragmentManager): Builder {
            this.fm = fm
            return this
        }

        /**
         * 生成 FragVpAdapter 对象
         */
        fun build(): FragVpAdapter {
            val adapter = FragVpAdapter(fm)
            adapter.mFrag = this.mFrag
            return adapter
        }
    }
}