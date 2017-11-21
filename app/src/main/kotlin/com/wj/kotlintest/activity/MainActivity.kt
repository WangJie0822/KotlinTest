package com.wj.kotlintest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import com.wj.kotlintest.R
import com.wj.kotlintest.adapter.FragVpAdapter
import com.wj.kotlintest.base.BaseActivity
import com.wj.kotlintest.base.BlankPresenter
import com.wj.kotlintest.constants.APP_EXIT_PRESS_BACK_INTERVAL
import com.wj.kotlintest.constants.MOVIES_TYPE_HIGHEST_RATE
import com.wj.kotlintest.constants.MOVIES_TYPE_POPULAR
import com.wj.kotlintest.databinding.ActivityMainBinding
import com.wj.kotlintest.fragment.MoviesListFragment
import com.wj.kotlintest.utils.AppManager

/**
 * 主界面
 */
class MainActivity : BaseActivity<BlankPresenter, ActivityMainBinding>() {

    /** 标记-第一次返回时间 */
    private var startMs: Long = 0L

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

    override fun onBackPressed() {
        // 获取按下时间
        val pressMs = System.currentTimeMillis()
        if ((pressMs - startMs) > APP_EXIT_PRESS_BACK_INTERVAL) {
            // 两次返回按下间隔超过设定时间，保存按下时间
            startMs = pressMs
            // 弹出提示
            val snackbar = Snackbar.make(mBinding.root, "连按两下返回退出应用", Snackbar.LENGTH_SHORT)
            @Suppress("DEPRECATION")
            snackbar.view.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            snackbar.show()
        } else {
            // 两次返回按下在设定时间内，退出应用
            AppManager.appExit()
        }
    }

    override fun initTitleBar() {
    }

}
