package com.wj.kotlintest.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Fragment ViewPager 适配器类
 */
class FragVpAdapter private constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private lateinit var mFrag: ArrayList<Fragment>

    override fun getItem(position: Int): Fragment {
        return mFrag[position]
    }

    override fun getCount(): Int {
        return mFrag.size
    }

    class Builder {

        private lateinit var mFrag: ArrayList<Fragment>

        private lateinit var fm: FragmentManager

        fun frags(frags: ArrayList<Fragment>): Builder {
            mFrag = frags
            return this
        }

        fun manager(fm: FragmentManager): Builder {
            this.fm = fm
            return this
        }

        fun build(): FragVpAdapter {
            val adapter = FragVpAdapter(fm)
            adapter.mFrag = this.mFrag
            return adapter
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}